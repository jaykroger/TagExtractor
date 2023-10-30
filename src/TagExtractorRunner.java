import javax.swing.*;

public class TagExtractorRunner
{
    public static void main(String[] args)
    {
        TagExtractor tagExtractor = new TagExtractor();

        tagExtractor.setTitle("Tag Extractor");
        tagExtractor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tagExtractor.setSize(1080, 560);
        tagExtractor.setLocation(100, 80);
        tagExtractor.setVisible(true);
    }
}