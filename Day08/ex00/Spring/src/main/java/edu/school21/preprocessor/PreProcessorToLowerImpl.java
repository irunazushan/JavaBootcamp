package edu.school21.preprocessor;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public String apply(String data) {
        return data.toLowerCase();
    }
}
