package com.amphyx.lab4;

import Lab4.Model.Sentences.Presentable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import Lab4.Model.StoryData;
import Lab4.Model.StoryModel;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class Lab4Controller {
    StoryModel s;

    {
        this.s = StoryModel.getInstance(StoryData.getSentences());
    }

    @GetMapping("/next")
    public String showNextData() {
        Presentable data = s.getNextData();
        if (data == null) {
            return "Data end";
        }
        String type = data.getType().toString();
        String place = data.getPlace() == null ? null : data.getPlace().toString();
        String character = capitalize(data.getCharacter().toString());
        String description;
        switch (data.getType()) {
            case ACTION:
                description = String.format("%s", data.getDescription());
                break;
            case PHRASE:
                description = String.format("говорит: %s", capitalize(data.getDescription()));
                break;
            case REACTION:
                if (data.getTargets().length > 0)
                    description = String.format("вызывает %s у", data.getDescription());
                else
                    description = String.format("чувствует %s", data.getDescription());
                break;
            default:
                description = "";
                break;
        }
        if (data.getTargets().length > 0)
            description += ":";
        String targets = Arrays.stream(data.getTargets()).map(Object::toString).collect(Collectors.joining(", "));
        return String.format("%s | Where: %s | %s %s | Targets: %s", type, place, character, description, targets);
    }

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
