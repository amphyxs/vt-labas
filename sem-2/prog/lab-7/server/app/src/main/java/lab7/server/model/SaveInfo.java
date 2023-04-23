package lab7.server.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaveInfo {

    private String saveLocation;
    private List<Integer> savedIds;
    private List<Integer> removedIds;
    private String message;

    public SaveInfo() {
        this.saveLocation = null;
        this.savedIds = new ArrayList<Integer>();
        this.removedIds = new ArrayList<Integer>();
        this.message = null;
    }

    public String getMessage() {
        return message;
    }

    public String getSaveLocation() {
        return saveLocation;
    }

    public int[] getSavedIds() {
        return this.savedIds.stream()
                                    .mapToInt(i -> i)
                                    .toArray();
    }

    public int[] getRemovedIds() {
        return this.removedIds.stream()
                                    .mapToInt(i -> i)
                                    .toArray();
    }

    public SaveInfo setMessage(String message) {
        this.message = message;
        return this;
    }

    public SaveInfo setSaveLocation(String saveLocation) {
        this.saveLocation = saveLocation;
        return this;
    }

    public SaveInfo setSavedIds(int[] savedIds) {
        this.savedIds = Arrays.stream(savedIds)
                                            .boxed()
                                            .collect(Collectors.toList());
        return this;
    }

    public SaveInfo setRemovedIds(int[] removedIds) {
        this.removedIds = Arrays.stream(removedIds)
                                            .boxed()
                                            .collect(Collectors.toList());
        return this;
    }

    public SaveInfo addSavedId(int newId) {
        this.savedIds.add(newId);
        return this;
    }

    public SaveInfo addRemovedId(int newId) {
        this.removedIds.add(newId);
        return this;
    }

}
