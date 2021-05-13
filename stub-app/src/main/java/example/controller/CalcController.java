package example.controller;

import example.service.CalcService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalcController {

    private final CalcService calcService;

    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @RequestMapping("/add")
    public int add(@RequestParam int x, @RequestParam int y) {
        return calcService.add(x, y);
    }

    @RequestMapping("/minus")
    public int minus(int x, int y) {
        return calcService.minus(x, y);
    }
}
