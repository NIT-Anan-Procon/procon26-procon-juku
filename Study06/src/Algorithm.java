
public interface Algorithm<I, O> {
	O maximize(I input, ObjectiveFunction<I> function);
}
