package org.lab5.Presenter.Commands;

import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;
import org.lab5.Model.DataClasses.Chapter;


/**
 * Команда вывода значений всех полей chapter объектов коллекции, отсортировав по убыванию
 */
public class PrintFieldDescendingChapterCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        List<Chapter> allChapters = presenter.getCollection().stream()
                                                                .map(e -> e.getChapter())
                                                                .collect(Collectors.toList());        
        Collections.sort(allChapters, Collections.reverseOrder());
        String result = allChapters.stream()
                                                    .map(Object::toString)
                                                    .collect(Collectors.joining("\n===\n"));
        presenter.getView().showResult(result);
    }

    @Override
    public String getName() {
        return "print_field_descending_chapter";
    }

    @Override
    public String getDescription() {
        return "вывести значения поля chapter всех элементов в порядке убывания";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
