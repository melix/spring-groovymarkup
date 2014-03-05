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

import groovy.text.Template;
import groovy.text.markup.MarkupTemplateEngine;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MarkupTemplateViewResolver extends AbstractCachingViewResolver implements Ordered {

    private LocaleAwareEngineFactory factory;
    private int order;
    private Map<String, Map<String, String>> viewModels = new HashMap<>();

    public LocaleAwareEngineFactory getFactory() {
        return factory;
    }

    public void setFactory(final LocaleAwareEngineFactory factory) {
        this.factory = factory;
    }

    @Override
    protected View loadView(final String viewName, final Locale locale) throws Exception {
        Map<String, String> model = viewModels.get(viewName);
        Template template;
        if (model != null) {
            template = factory.getEngine(locale).createTypeCheckedModelTemplateByPath(createTemplatePath(viewName, locale), model);
        } else {
            template = factory.getEngine(locale).createTemplateByPath(createTemplatePath(viewName, locale));
        }
        return new GroovyTemplateEngineView(template);
    }

    private String createTemplatePath(final String viewName, final Locale locale) {
        MarkupTemplateEngine.TemplateResource resource = MarkupTemplateEngine.TemplateResource.parse(viewName);
        resource.withLocale(locale.toString().replace("-", "_"));
        return resource.toString();
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public Map<String, Map<String, String>> getViewModels() {
        return viewModels;
    }

    public void setViewModels(final Map<String, Map<String, String>> viewModels) {
        this.viewModels = viewModels;
    }
}
