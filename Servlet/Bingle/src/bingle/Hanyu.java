package bingle;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Hanyu
{
    private HanyuPinyinOutputFormat format = null;
    private String[] pinyin;

    public Hanyu()
    {
        this.format = new HanyuPinyinOutputFormat();

        this.format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);



        this.pinyin = null;
    }

    public String getCharacterPinYin(char c)
    {
        try
        {
            this.pinyin = PinyinHelper.toHanyuPinyinStringArray(c, this.format);
        }
        catch (BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
        }
        if (this.pinyin == null) {
            return null;
        }
        return this.pinyin[0];
    }

    public String getStringPinYin(String str)
    {
        StringBuilder sb = new StringBuilder();

        String tempPinyin = null;
        for (int i = 0; i < str.length(); i++)
        {
            tempPinyin = getCharacterPinYin(str.charAt(i));
            if (tempPinyin == null) {
                sb.append(str.charAt(i));
            } else {
                sb.append(tempPinyin);
            }
        }
        return sb.toString();
    }
}