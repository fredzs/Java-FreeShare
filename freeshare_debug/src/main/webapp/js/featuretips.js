//鼠标停留在搜索框上显示tip，用来提示搜索的更多功能。

$("#bigquery").focus(
		function()
		{
		$("#featuretip2").addClass('hidden');
		$("#searchpagecancel").click(
			function()
			{
				$("#featuretip2").removeClass('hidden');
			});
		}
	);