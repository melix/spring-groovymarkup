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
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import java.util.Locale;

public class MarkupTemplateViewResolver extends AbstractCachingViewResolver implements Ordered {

    public final static String DEFAULT_BASENAME = "views";

    private String baseName = DEFAULT_BASENAME;

    private MarkupTemplateEngine engine;
    private int order;

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(final String baseName) {
        this.baseName = baseName;
    }

    public MarkupTemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(final MarkupTemplateEngine engine) {
        this.engine = engine;
    }

    @Override
    protected View loadView(final String viewName, final Locale locale) throws Exception {
        return new GroovyTemplateEngineView(engine.createTemplateByPath(createTemplatePath(viewName, locale)));
    }

    private String createTemplatePath(final String viewName, final Locale locale) {
        MarkupTemplateEngine.TemplateResource resource = MarkupTemplateEngine.TemplateResource.parse(baseName+"/"+viewName);
        resource.withLocale(locale.toLanguageTag().replace("-","_"));
        return resource.toString();
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
