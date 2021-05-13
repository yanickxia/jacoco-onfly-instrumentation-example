package example.reader;

import io.netty.buffer.ByteBufOutputStream;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.data.ExecutionDataWriter;
import org.jacoco.core.runtime.RemoteControlReader;
import org.jacoco.core.runtime.RemoteControlWriter;
import org.jacoco.core.tools.ExecFileLoader;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Reader {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 8084;

    public static void main(final String[] args) throws IOException {

        final ByteArrayOutputStream localFile = new ByteArrayOutputStream();
        final ExecutionDataWriter localWriter = new ExecutionDataWriter(localFile);

        // Open a socket to the coverage agent:
        final Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
        final RemoteControlWriter writer = new RemoteControlWriter(
                socket.getOutputStream());
        final RemoteControlReader reader = new RemoteControlReader(
                socket.getInputStream());
        reader.setSessionInfoVisitor(localWriter);
        reader.setExecutionDataVisitor(localWriter);

        // Send a dump command and read the response:
        writer.visitDumpCommand(true, false);
        if (!reader.read()) {
            throw new IOException("Socket closed unexpectedly.");
        }

        socket.close();
        final InputStream inputStream = new ByteArrayInputStream(localFile.toByteArray());
        localFile.close();

        ExecFileLoader execFileLoader = new ExecFileLoader();
        execFileLoader.load(inputStream);

        final CoverageBuilder coverageBuilder = new CoverageBuilder();
        final Analyzer analyzer = new Analyzer(execFileLoader.getExecutionDataStore(), coverageBuilder);

        analyzer.analyzeAll(new File("/Users/yanick/Codes/java/jacoco-onfly-instrumentation-example/stub-app/target/classes"));

        IBundleCoverage coverageBuilderBundle = coverageBuilder.getBundle("result");
        System.out.println(coverageBuilderBundle);
    }
}
