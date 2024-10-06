@template.layout(title = "company", content = @`

    <h1> Hello World! </h1>
    <div id="replace_me"> </div>
    <form>
        <select name="n_companies"  hx-get="/show_companies" hx-target="#replace_me" >
            <option ></option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="30">30</option>
        </select>
        <button class="btn primary" hx-get="/show_companies" hx-target="#replace_me" hx-swap="innerHTML"> click me </button>
    </form>
`)