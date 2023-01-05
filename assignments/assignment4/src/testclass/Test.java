package testclass;

import dependency_injection.Value;

import java.util.List;
import java.util.Set;

public class Test {
    @Value("[]")
    private String[] val;

    public String[] getVal() {
        return val;
    }
}