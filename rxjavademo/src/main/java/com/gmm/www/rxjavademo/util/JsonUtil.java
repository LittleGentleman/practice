package com.gmm.www.rxjavademo.util;

/**
 * @author:gmm
 * @date:2020/2/23
 * @类说明:
 */
public class JsonUtil {

    /**
     * 格式化json字符串
     *
     * @param jsonStr
     * @return
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;//记录缩进的个数
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                //遇到{ [换行，且下一行缩进
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                //遇到} ]换行，当前行缩进
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                //遇到，换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 添加space  达到缩进效果
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
