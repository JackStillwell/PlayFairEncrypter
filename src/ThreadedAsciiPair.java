public class ThreadedAsciiPair {

    public AsciiPair asciiPair;
    public int index;

    public ThreadedAsciiPair(AsciiPair _asciiPair, int _index)
    {
        asciiPair = _asciiPair;
        index = _index;
    }

    public String toString()
    {
        return asciiPair.one + " : " + asciiPair.two + " @ " + index;
    }

}
