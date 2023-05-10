package me.ding.expressionspbt.controller;

import me.ding.expressionspbt.service.ExpSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ExpCon {
    @Autowired
    private ExpSer expSer;

    @RequestMapping("/getQuestion")
    public String getQuestion() {
        return expSer.getQuestion();
    }

    @PostMapping("/check")
    public String checkAnswer(@RequestParam("question") String question, @RequestParam("input") String input) {
        return expSer.checkAnswer(question, input);
    }

    @RequestMapping("/doPass")
    public String Pass(@RequestParam("question") String question) {
        return expSer.pass(question);
    }

    @RequestMapping("/doStrange")
    public String Strange(@RequestParam("question") String question) {
        return expSer.strange(question);
    }

    @RequestMapping("/doReset")
    public String Reset() {
        return expSer.reset();
    }
}
