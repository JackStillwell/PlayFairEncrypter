public class AsciiPair
{
    public Integer one;
    public Integer two;

    public AsciiPair(Integer _one, Integer _two)
    {
        one = _one;
        two = _two;
    }

    public String toString() {
        String toReturn = one + ":" + two;

        return toReturn;
    }
}
