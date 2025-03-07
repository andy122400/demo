package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agile.api.IDataObject;
import com.example.demo.service.CarolineService;
import com.example.demo.service.Test;
import com.example.demo.service.changeObjectNameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Agile 大平台", description = "提供一些功能")
@RestController
public class AgileController {

    @Autowired
    private Test test;
    @Autowired
    private CarolineService carolineService;

    @Autowired
    private changeObjectNameService changeObjectNameService;

    @Operation(summary = "Bulk Change Bom", description = "提供用戶 批次取代BOM")
    @GetMapping("/Accton/Agile/doCaroline")
    public String doCaroline(@RequestParam String change, @RequestParam String src, @RequestParam String replace)
            throws Exception {

        // List<String> list = carolineService.doAction(change, src, replace);

        // return "執行[" + src + "]替代成[" + replace
        // + "]\t總共" + list.size() + "個料:\n" + list.toString();

        List<String> list = carolineService.doAction(change, src, replace);

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html lang=\"zh-TW\">");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\">");
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        html.append("<title>Agile 替代料執行結果</title>");
        html.append(
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
        html.append("<style>");
        html.append("body { background-color: #f8f9fa; padding: 20px; }");
        html.append(
                ".container { max-width: 600px; background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<div class=\"container text-center\">");
        html.append("<h2 class=\"text-primary\">替代料執行結果</h2>");
        html.append("<p class=\"lead\">執行 <strong>[").append(src).append("]</strong> 替代成 <strong>[").append(replace)
                .append("]</strong></p>");
        html.append("<p>總共成功 <strong>").append(list.size()).append("</strong> 個料:</p>");
        html.append("<ul class=\"list-group\">");
        for (String item : list) {
            html.append("<li class=\"list-group-item\">").append(item).append("</li>");
        }
        html.append("</ul>");
        html.append("<a href=\"/\" class=\"btn btn-primary mt-3\">讚啦~(別按拜託)</a>");
        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    @GetMapping("/Accton/Agile")
    public String changeName()
            throws Exception {

        return "^__^?";
    }

    @GetMapping("/Accton/Agile/changeObjectName")
    public String changeName(@RequestParam String A, @RequestParam String B)
            throws Exception {
        changeObjectNameService.changeObjectName(A, B);

        return "成功將表單A改名為B";
    }

    @GetMapping("/Accton/Agile/test")
    public String test() throws Exception {
        test.test();
        return "test";
    }
}
