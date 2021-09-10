<#assign content>
    <h1 class="subtitle">Here are the results of your query:</h1>
    <#list output>
        <ul class="star-list">
            <#items as line>
                <li class="stars-item">
                    <span class="star-row"><pre class="results-row">${line}</pre></span>
                </li>
            </#items>
        </ul>
    <#else>
        <h1 class="subtitle">Your query returned no results :(</h1>
    </#list>
</#assign>
<#include "main.ftl">
