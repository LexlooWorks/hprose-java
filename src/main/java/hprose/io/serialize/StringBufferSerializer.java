/**********************************************************\
|                                                          |
|                          hprose                          |
|                                                          |
| Official WebSite: http://www.hprose.com/                 |
|                   http://www.hprose.org/                 |
|                                                          |
\**********************************************************/
/**********************************************************\
 *                                                        *
 * StringBufferSerializer.java                            *
 *                                                        *
 * StringBuffer serializer class for Java.                *
 *                                                        *
 * LastModified: Apr 17, 2016                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
\**********************************************************/

package hprose.io.serialize;

import static hprose.io.HproseTags.TagEmpty;
import static hprose.io.HproseTags.TagString;
import java.io.IOException;
import java.io.OutputStream;

final class StringBufferSerializer implements Serializer<StringBuffer> {

    public final static StringBufferSerializer instance = new StringBufferSerializer();

    public final static void write(OutputStream stream, WriterRefer refer, StringBuffer s) throws IOException {
        if (refer != null) refer.set(s);
        stream.write(TagString);
        ValueWriter.write(stream, s.toString());
    }

    public final void write(Writer writer, StringBuffer obj) throws IOException {
        OutputStream stream = writer.stream;
        switch (obj.length()) {
            case 0:
                stream.write(TagEmpty);
                break;
            case 1:
                ValueWriter.write(stream, obj.charAt(0));
                break;
            default:
                WriterRefer refer = writer.refer;
                if (refer == null || !refer.write(stream, obj)) {
                    write(stream, refer, obj);
                }
                break;
        }
    }
}
