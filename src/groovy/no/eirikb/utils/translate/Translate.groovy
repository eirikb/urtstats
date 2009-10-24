/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.utils.translate

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.logging.Level
import java.util.logging.Logger
import org.htmlparser.Node
import org.htmlparser.Parser
import org.htmlparser.filters.HasAttributeFilter
import org.htmlparser.util.NodeList
import org.htmlparser.util.ParserException

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class Translate {
    static def translate(text) {
        try {
            Parser parser = new Parser("http://translate.google.com/translate_t?sl=auto&tl=en&text=" + URLEncoder.encode(text, "UTF-8"));
            NodeList list = parser.parse(new HasAttributeFilter("id", "result_box"));
            for (Node node : list.toNodeArray()) {
                return node.getFirstChild().getText();
            }
        } catch (Exception e) {}
        return null
    }
}

