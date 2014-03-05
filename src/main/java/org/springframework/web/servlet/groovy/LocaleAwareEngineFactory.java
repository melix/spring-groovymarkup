/*
 * Copyright 2003-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.web.servlet.groovy;

import groovy.text.markup.MarkupTemplateEngine;
import groovy.text.markup.TemplateConfiguration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Spring MVC asks for views with a locale, but the template engine makes use of its configuration
 * to get an included file. To workaround this, it is possible to use one engine per locale.
 */
public class LocaleAwareEngineFactory {
    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<String, MarkupTemplateEngine> engines = new HashMap<String, MarkupTemplateEngine>();

    private ClassLoader parent;
    private TemplateConfiguration configuration;

    public TemplateConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(final TemplateConfiguration configuration) {
        this.configuration = configuration;
    }

    public ClassLoader getParent() {
        return parent;
    }

    public void setParent(final ClassLoader parent) {
        this.parent = parent;
    }

    public MarkupTemplateEngine getEngine(Locale locale) {
        String localeString = locale.toString();
        MarkupTemplateEngine engine = engines.get(localeString);
        if (engine!=null) {
            return engine;
        }
        return lazyLoadEngine(locale);
    }

    private MarkupTemplateEngine lazyLoadEngine(final Locale locale) {
        MarkupTemplateEngine engine = null;
        try {
            lock.readLock().lock();
            engine = engines.get(locale.toString());
            if (engine==null) {
                lock.readLock().unlock();
                try {
                    lock.writeLock().lock();
                    engine = engines.get(locale.toString());
                    if (engine==null) {
                        TemplateConfiguration withLocale = cloneConfigurationWithLocale(locale);
                        engine = new MarkupTemplateEngine(parent, withLocale);
                    }
                } finally {
                    lock.readLock().lock();
                    lock.writeLock().unlock();
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        return engine;
    }

    private TemplateConfiguration cloneConfigurationWithLocale(final Locale locale) {
        TemplateConfiguration clone = new TemplateConfiguration();
        clone.setAutoEscape(configuration.isAutoEscape());
        clone.setAutoIndent(configuration.isAutoIndent());
        clone.setAutoIndentString(configuration.getAutoIndentString());
        clone.setAutoNewLine(configuration.isAutoNewLine());
        clone.setBaseTemplateClass(configuration.getBaseTemplateClass());
        clone.setDeclarationEncoding(configuration.getDeclarationEncoding());
        clone.setExpandEmptyElements(configuration.isExpandEmptyElements());
        clone.setLocale(locale); // only change!
        clone.setNewLineString(configuration.getNewLineString());
        clone.setUseDoubleQuotes(configuration.isUseDoubleQuotes());
        return clone;
    }
}
