/*
 * "THE BEER-WARE LICENSE" (Revision 42):
 * =============================================================================
 * <eirikb@eirkb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */

package no.eirikb.utils

import org.htmlparser.Node
import org.htmlparser.Parser
import org.htmlparser.filters.StringFilter
import org.htmlparser.util.ParserException

private static String getLocation(String ip) {
    try {
        Parser parser = new Parser("http://www.geoiptool.com/en/?IP=" + ip)
        return "Country: " + getElementText(parser, "Country:") + ". City: " + getElementText(parser, "City:")
    } catch (Exception e) {
        e.printStackTrace()
    }
    return null;
}

private static String getElementText(Parser parser, String elementName) throws ParserException {
    parser.reset();
    Node node = parser.parse(new StringFilter(elementName)).elementAt(0).getParent().getParent().
        getParent().getChildren().elementAt(3).getChildren().elementAt(0)
    if (node.getChildren() != null) {
        return node.getChildren().elementAt(0).getText().trim()
    } else {
        return node.getText()
    }
}