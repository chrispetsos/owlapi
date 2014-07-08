package org.semanticweb.owlapi.io;

import java.util.List;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;

/**
 * Generic parser factory.
 * 
 * @author ignazio
 */
public abstract class OWLParserFactoryImpl implements OWLParserFactory {

    private static final long serialVersionUID = 40000L;
    private OWLDocumentFormatFactory format;

    protected OWLParserFactoryImpl(OWLDocumentFormatFactory format) {
        this.format = format;
    }

    @SuppressWarnings("null")
    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return format;
    }

    @Override
    public final OWLParser get() {
        return createParser();
    }

    @Nullable
    @Override
    public final String getDefaultMIMEType() {
        return format.getDefaultMIMEType();
    }

    @Override
    public final List<String> getMIMETypes() {
        return format.getMIMETypes();
    }

    @Override
    public final boolean handlesMimeType(String mimeType) {
        return format.handlesMimeType(mimeType);
    }
}
