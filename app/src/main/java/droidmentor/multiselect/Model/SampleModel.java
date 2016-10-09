package droidmentor.multiselect.Model;

public class SampleModel {
    private String name;
    private String posting;

    public SampleModel(String name, String posting) {
        this.name = name;
        this.posting = posting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }
}
