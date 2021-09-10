<#assign content>

<div class="tab" style="margin-top:110px">
    <button type="button" class="tabButtons" onclick="openTab(event, 'searchByName')">Search by name</button>
    <button type="button" class="tabButtons" onclick="openTab(event, 'searchByCoordinates')">Search by coordinates</button>
</div>

<div id="searchByName" class="tabPages">
    <form method="POST" action="/radius-results1" class="tabForm">
    <span class="inputLine">
        <h1 class="tabText">What radius would you like to search within?: </h1>
    <textarea name="radius" rows="1" class="num-input"></textarea></span>
    <span class="inputLine"><h1 class="tabText">Name: </h1>
        <textarea name="starName" rows="1" class="string-input"></textarea></span>
        <div class="checkboxDiv">
            <label>
                <input type="checkbox" name="isNaive"/>
                <span>Would you like to perform a naive (slower) search?</span>
            </label>
        </div>
        <span class="btnContainer">
          <button class="btn-floating btn-large waves-effect waves-light green goBtn" type="submit">GO</button>
    </span>
    </form>
</div>

<div id="searchByCoordinates" class="tabPages">
    <form method="POST" action="/radius-results2" class="tabForm">
        <span class="inputLine">
        <h1 class="tabText">What radius would you like to search within?: </h1>
        <textarea name="radius" rows="1" class="num-input"></textarea></span>
    <span class="inputLine"><h1 class="tabText">X: </h1>
        <textarea name="xCoords" rows="1" class="num-input" type="number"></textarea>
        <h1 class="tabText">Y: </h1>
        <textarea name="yCoords" rows="1" class="num-input" type="number"></textarea>
        <h1 class="tabText">Z: </h1>
        <textarea name="zCoords" rows="1" class="num-input" type="number"></textarea></span>
        <div class="checkboxDiv">
            <label>
                <input type="checkbox" name="isNaive"/>
                <span>Would you like to perform a naive (slower) search?</span>
            </label>
        </div>
        <span class="btnContainer">
          <button class="btn-floating btn-large waves-effect waves-light green goBtn" type="submit">GO</button>
    </span>
    </form>
</div>


</#assign>
<#include "main.ftl">