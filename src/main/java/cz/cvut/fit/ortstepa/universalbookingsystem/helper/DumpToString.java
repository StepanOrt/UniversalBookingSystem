package cz.cvut.fit.ortstepa.universalbookingsystem.helper;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
 
public class DumpToString {
 
        public static String dump(Object o){
        		String string = null;
				try {
					StringBuilder out = new StringBuilder();
					out.append("\ndump[\n");
					dump(o, out);
					out.append("\n]");
					string = out.toString();
				} catch (Exception e) {
					string = e.toString();
				}
				return string;
        }
 
        public static void dump(Object o, StringBuilder out) throws Exception {
        	dump(o, out, 0, new HashSet<Object>());
        }
        
        
        private static void dump(Object o, StringBuilder out, int indent, Set<Object> done) throws Exception {
                if (o == null) {
                	out.append("<null>");
                	return;
                }
                try {
                	if (done.contains(o) && !isPrimitive(o)) {
                			out.append("<overflow_protection@" + o.hashCode() + ">");
                			return;
                	} else done.add(o);
                } catch (Exception e) {}
                if (o.getClass().isArray()) {
                        out.append("arr[");
                        for (int i = 0; i < Array.getLength(o); i++) {
                                if (i > 0) {
                                		out.append(",");
                                        out.append("\n");
                                        indent(indent+1, out);
                                }
                                dump(Array.get(o, i), out, indent + 1, done);
                        }
                        out.append("]");
                } else if (o instanceof Iterable) {
                        Iterator<Object> i = ((Iterable<Object>) o).iterator();
                        out.append("list[");
                        int index = 0;
                        while (i.hasNext()) {
                                if (index > 0) {
                                		out.append(",");
                                        out.append("\n");
                                        indent(indent, out);
                                }
                                dump(i.next(), out, indent + 1, done);
                                index++;
                        }
                        out.append("\n");
                        indent(indent, out);
                        out.append("]");
                } else {
                        String name = o.getClass().getName();
                        if (isPrimitive(o)) {
                                out.append(o.toString());
                        } else {
                                out.append(name+"@"+o.hashCode());
                                out.append("{\n");
                                indent(indent + 1, out);
                                Class<? extends Object>  oClass = o.getClass();
                                int i2 = 0;
                                while (oClass != null) {
                                        if (isPrimitive(o)) {
                                                if (i2 > 0) {
                                                		out.append(",");
                                                        out.append("\n");
                                                        indent(indent + 1, out);
                                                } else
                                                        i2++;
                                                out.append("toString()=" + o.toString());
                                                break;
                                        }
                                        Field[] fields = oClass.getDeclaredFields();
 
                                        for (int i = 0; i < fields.length; i++) {
                                                fields[i].setAccessible(true);
                                                {
                                                        int mod = fields[i].getModifiers();
                                                        if (Modifier.isStatic(mod) && Modifier.isFinal(mod))
                                                                continue;
                                                }
                                                Object value = fields[i].get(o);
                                                if (value != null) {
                                                        if (i2 > 0) {
                                                        		out.append(",");
                                                                out.append("\n");
                                                                indent(indent + 1, out);
                                                        } else
                                                                i2++;
                                                        out.append(fields[i].getName());
                                                        out.append("=");
                                                        dump(value, out, indent + 1, done);
                                                }
                                        }
                                        oClass = oClass.getSuperclass();
                                }
                                out.append("\n");
                                indent(indent, out);
                                out.append("}");
                        }
                }
        }
        
        private static void indent(int i, StringBuilder out) throws IOException {
                for (; i > 0; i--)
                        out.append("\t");
        }
        
        private static boolean isPrimitive(Object o) {
        		String className = o.getClass().getName();
                return className.startsWith("java.") || className.startsWith("javax.")
                                || className.indexOf(".") < 0;
        }
}