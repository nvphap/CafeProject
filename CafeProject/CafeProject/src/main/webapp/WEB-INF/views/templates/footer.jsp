<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
	<!--Basic Scripts-->
	<script src="${pageContext.request.contextPath}/resources/azweb/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/azweb/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap-dialog.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap-dialog.min.css" rel="stylesheet" type="text/css" />    
	
	<!--Scroll Scripts-->
    <script src="${pageContext.request.contextPath}/resources/azweb/js/jquery.slimscroll.min.js"></script>

    <!--Beyond Scripts-->
    <script src="${pageContext.request.contextPath}/resources/azweb/js/beyond.js"></script>
    
	<!-- Bootstrap multiselect [-- Do not remove --]-->
	<script src="${pageContext.request.contextPath}/resources/bootstrap-multiselect-2.0/bootstrap-multiselect.js"></script>

	
	
	
    <!--Google Analytics::Demo Only-->
    <script>
        (function (i, s, o, g, r, a, m) {
            i['GoogleAnalyticsObject'] = r; i[r] = i[r] || function () {
                (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date(); a = s.createElement(o),
            m = s.getElementsByTagName(o)[0]; a.async = 1; a.src = g; m.parentNode.insertBefore(a, m)
        })(window, document, 'script', 'http://www.google-analytics.com/analytics.js', 'ga');

        ga('create', 'UA-60412744-1', 'auto');
        ga('send', 'pageview');

    </script>
    <div id="flotTip" style="display: none; position: absolute;"></div>
</body>
</html>
