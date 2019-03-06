/*
 * Copyright (C) 2014 BigTesting.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bigtesting.routd;

/**
 * 
 * @author Luis Antunes
 */
public interface Router {

    void add(Route route);
    
    /**
     * Returns a Route that matches the given URL path.
     * Note that the path may be expected to be an undecoded
     * URL path. This URL encoding requirement is determined
     * by the Router implementation.
     * 
     *  @param path a decoded or undecoded URL path, 
     *              depending on the Router implementation
     *  @return the matching route, or null if none is found
     */
    Route route(String path);
}
