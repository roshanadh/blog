package np.com.roshanadhikary.blog.util;

import org.commonmark.node.*;
import org.commonmark.parser.*;
import org.commonmark.renderer.html.*;

import java.util.*;

public class MdToHtmlRenderer {
	/**
	 * Parse markdown string passed as argument, and render
	 * resulting HTML
	 */
	public String render(List<String> markdownLines) {
		Parser parser = Parser.builder().build();
		HtmlRenderer renderer = HtmlRenderer.builder().build();

		StringBuilder renderedSB = new StringBuilder();
		for (String markdownLine : markdownLines) {
			Node document = parser.parse(markdownLine);
			renderedSB.append(renderer.render(document));
		}
		return new String(renderedSB);
	}
}
