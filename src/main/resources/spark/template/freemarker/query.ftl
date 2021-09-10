<#assign content>

<div class="typewriter subtitle">
  <h1>How would you like to search for stars?</h1>
</div>

<div class="grid">
  <button onclick="window.location.href='/neighbors'" class="search_option_btn">Search for neighbors</button>
  <button onclick="window.location.href='/radius'" class="search_option_btn">Search by radius</button>
</div>

<div class="star-list">
  <label class="subtitle">Star Data:</label>
  <#list stars>
    <ul>
      <#items as star>
        <li class="stars-item">
          <span class="star-row"><pre class="results-row">${star}</pre></span>
        </li>
      </#items>
    </ul>
  <#else>
    <h1 class="results_title">No stars loaded...</h1>
  </#list>
</div>


</#assign>
<#include "main.ftl">