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
 * CalendarSerializer.java                                *
 *                                                        *
 * Calendar serializer class for Java.                    *
 *                                                        *
 * LastModified: Apr 17, 2016                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
\**********************************************************/

package hprose.io.serialize;

import static hprose.io.HproseTags.TagSemicolon;
import static hprose.io.HproseTags.TagUTC;
import hprose.util.TimeZoneUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.TimeZone;

final class CalendarSerializer implements Serializer<Calendar> {

    public final static CalendarSerializer instance = new CalendarSerializer();

    public final static void write(OutputStream stream, WriterRefer refer, Calendar calendar) throws IOException {
        if (refer != null) refer.set(calendar);
        TimeZone tz = calendar.getTimeZone();
        if (!(tz.hasSameRules(TimeZoneUtil.DefaultTZ) || tz.hasSameRules(TimeZoneUtil.UTC))) {
            tz = TimeZoneUtil.UTC;
            Calendar c = (Calendar) calendar.clone();
            c.setTimeZone(tz);
            calendar = c;
        }
        ValueWriter.writeDateOfCalendar(stream, calendar);
        ValueWriter.writeTimeOfCalendar(stream, calendar, true, false);
        stream.write(tz.hasSameRules(TimeZoneUtil.UTC) ? TagUTC : TagSemicolon);
    }

    public final void write(Writer writer, Calendar obj) throws IOException {
        OutputStream stream = writer.stream;
        WriterRefer refer = writer.refer;
        if (refer == null || !refer.write(stream, obj)) {
            write(stream, refer, obj);
        }
    }
}
