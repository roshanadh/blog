package np.com.roshanadhikary.blog.util;

import org.commonmark.node.*;
import org.commonmark.parser.*;
import org.commonmark.renderer.html.*;

public class MdToHtmlRenderer {
	/**
	 * Parse markdown string passed as argument, and render
	 * resulting HTML
	 */
	public String render(String markdown) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);
	}
}
