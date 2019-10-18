package tp.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class ListeOperations {
    private List<Operation> lo = new ArrayList<Operation>();

    public ListeOperations() {}

    public void add(Operation o) {
        lo.add(o);
    }

    public Operation pop() {
        return lo.get(lo.size()-1);
    }

    @Override
    public String toString() {
        return "ListeOperations{" +
                "lo=" + lo +
                '}';
    }

    public List<Operation> getLo() {
        return lo;
    }
}
