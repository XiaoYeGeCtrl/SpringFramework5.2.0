/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.scripting.support;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;
import org.springframework.scripting.ScriptSource;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

/**
 * {@link ScriptSource} implementation
 * based on Spring's {@link Resource}
 * abstraction. Loads the script text from the underlying Resource's
 * {@link Resource#getFile() File} or
 * {@link Resource#getInputStream() InputStream},
 * and tracks the last-modified timestamp of the file (if possible).
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @see Resource#getInputStream()
 * @see Resource#getFile()
 * @see org.springframework.core.io.ResourceLoader
 * @since 2.0
 */
public class ResourceScriptSource implements ScriptSource {

    /**
     * Logger available to subclasses.
     */
    protected final Log logger = LogFactory.getLog(getClass());
    private final Object lastModifiedMonitor = new Object();
    private EncodedResource resource;
    private long lastModified = -1;


    /**
     * Create a new ResourceScriptSource for the given resource.
     *
     * @param resource the EncodedResource to load the script from
     */
    public ResourceScriptSource(EncodedResource resource) {
        Assert.notNull(resource, "Resource must not be null");
        this.resource = resource;
    }

    /**
     * Create a new ResourceScriptSource for the given resource.
     *
     * @param resource the Resource to load the script from (using UTF-8 encoding)
     */
    public ResourceScriptSource(Resource resource) {
        Assert.notNull(resource, "Resource must not be null");
        this.resource = new EncodedResource(resource, "UTF-8");
    }


    /**
     * Return the {@link Resource} to load the
     * script from.
     */
    public final Resource getResource() {
        return this.resource.getResource();
    }

    /**
     * Set the encoding used for reading the script resource.
     * <p>The default value for regular Resources is "UTF-8".
     * A {@code null} value implies the platform default.
     */
    public void setEncoding(@Nullable String encoding) {
        this.resource = new EncodedResource(this.resource.getResource(), encoding);
    }


    @Override
    public String getScriptAsString() throws IOException {
        synchronized (this.lastModifiedMonitor) {
            this.lastModified = retrieveLastModifiedTime();
        }
        Reader reader = this.resource.getReader();
        return FileCopyUtils.copyToString(reader);
    }

    @Override
    public boolean isModified() {
        synchronized (this.lastModifiedMonitor) {
            return (this.lastModified < 0 || retrieveLastModifiedTime() > this.lastModified);
        }
    }

    /**
     * Retrieve the current last-modified timestamp of the underlying resource.
     *
     * @return the current timestamp, or 0 if not determinable
     */
    protected long retrieveLastModifiedTime() {
        try {
            return getResource().lastModified();
        } catch (IOException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(getResource() + " could not be resolved in the file system - " +
                        "current timestamp not available for script modification check", ex);
            }
            return 0;
        }
    }

    @Override
    @Nullable
    public String suggestedClassName() {
        String filename = getResource().getFilename();
        return (filename != null ? StringUtils.stripFilenameExtension(filename) : null);
    }

    @Override
    public String toString() {
        return this.resource.toString();
    }

}
