import java.util.HashSet;
import java.util.Set;

public class test {

    char first, second;

    public test(char a, char b){
        first = a;
        second = b;
    }

    public boolean equals(test b){
        return b.hashCode() == this.hashCode();
    }

    public int hashCode() {
        return first*31 + second;
    }

    public static void main(String[] args) {
        Set<test> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                test t = new test(j, j);
                boolean flag = false;
                for (test test : set) {
                    if (test.equals(t))
                        flag = true;
                }
                if (!flag) {
                    set.add(t);
                }
            }
        }
        System.out.println(set.size());
    }
}
