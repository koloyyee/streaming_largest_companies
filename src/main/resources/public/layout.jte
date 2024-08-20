@import gg.jte.Content

@param String title
@param Content content

<html>
<head>
<%--        <script src="/webjars/htmx.org/2.0.2/dist/htmx.min.js"></script>--%>
        <script src="https://unpkg.com/htmx.org@2.0.2" integrity="sha384-Y7hw+L/jvKeWIRRkqWYfPcvVxHzVzn5REgzbawhxAuQGwX1XWe70vji+VSeHOThJ"  crossorigin="anonymous"></script>

        <title>${title}</title>
    </head>
<body>
    ${content}
</body>

</html>