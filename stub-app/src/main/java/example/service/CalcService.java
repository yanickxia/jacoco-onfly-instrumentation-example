package example.service;

import org.springframework.stereotype.Service;

@Service
public class CalcService {

    public int add(int x, int y) {
        return x + y;
    }

    public int minus(int x, int y) {
        return x - y;
    }
}
