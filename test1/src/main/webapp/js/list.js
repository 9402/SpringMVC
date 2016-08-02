$(function() {

	var page_num = 0;
	var pagesize = 10;
	var offset = 0;
	var total = 0;
	var lastpage = 0;
	var indexs = 0;
	var half = 0;

	pageAjax();

	function pageAjax() {
		offset = page_num * pagesize;
		$.ajax({
			type : "POST",
			url : "listCustomers.action",
			data : "offset=" + offset + "&pagesize=" + pagesize,
			dataType : "json",
			success : function(data) {
				total = data.json_Customers.total;
				lastpage = data.json_Customers.lastpage;
				indexs = data.json_Customers.indexs;
				half = data.json_Customers.half;
				flushTable(data.json_Customers.data);
			}
		});
		showButtons(8);
	}
	
	$("#dhn-pagebuttons li > .inli").each(function() {
		$(this).click(function() {
			theView($(this).attr("id"));
		});
	});
	$("#dhn-pagebuttons .previous").click(prevView);
	$("#dhn-pagebuttons .next").click(nextView);

	function showButtons(maxindexs) {
	//	$("#dhn-pagebuttons li").remove();
	//	var pagebtns = $("#dhn-pagebuttons ul");
	//	var prev = $("<li class='previous'><a href='fakelink' class='fui-arrow-left'></a></li>");
	//	var next = $("<li class='next'><a href='fakelink' class='fui-arrow-right'></a></li>");
	//	pagebtns.append(prev);

		if (page_num < (maxindexs / 2)) {
			$("#dhn-pagebuttons .inli").each(function(index, item) {
//				$(this).attr("id", index);
//				$(this).text(index+1);
			});
//			for (var i = 0; i < maxindexs; i++) {
//				pagebtns.append($("<li><a href='#' id=" + i + ">" + (i + 1)
//						+ "</a></li>"));
//			}
		} else if (page_num > lastpage - half) {
			$("#dhn-pagebuttons .inli").each(function(index, item) {
				$(this).attr("id", lastpage-maxindexs+index+1);
				$(this).text(lastpage-maxindexs+index+2);
			});
//			for (var i = maxindexs - 1; i >= 0; i--) {
//				pagebtns.append($("<li><a class='inli' id=" + (lastpage - i) + ">"
//						+ (lastpage - i + 1) + "</a></li>"));
//			}
		} else {
			
			$("#dhn-pagebuttons .inli").each(function(index, item) {
				$(this).attr("id", page_num-half+index);
				$(this).text(page_num-half+index+1);
			});
	
//			for (var i = 0; i < maxindexs; i++) {
				
				
//				pagebtns.append($("<li><a class='inli' id=" + (page_num - half + i) + ">"
//						+ (page_num - half + i + 1) + "</a></li>"));
//			}
		}
		
		$("#dhn-pagebuttons li").removeClass("active");
	//	pagebtns.append(next);

		
//		$("#dhn-pagebuttons li > .inli").each(function() {
//			$(this).click(function() {
//				theView($(this).attr("id"));
//			});
//		});
//		$("#dhn-pagebuttons .previous").click(prevView);
//		$("#dhn-pagebuttons .next").click(nextView);
		$("#dhn-pagebuttons ul li").each(function() {
			if ($(this).find(".inli").attr("id") == page_num) {
				$(this).addClass("active");
			}
		});
	}

	function nextView() {
		if (page_num >= lastpage) {
			return;
		} else {
			page_num++;
			pageAjax();
		}
	}

	function prevView() {
		if (page_num == 0) {
			return;
		} else {
			page_num--;
			pageAjax();
		}
	}

	function theView(page) {
		page_num = page;
		pageAjax();
		return;
	}

	function flushTable(data) {
		var tbody = $("#dhn-table-body");
		$("#dhn-table-body tr").remove();

		for ( var i in data) {
			var tr = createTr();
			var caozuo = createTd("<a href=''>删除</a><a href=''>修改</a>")
			var firstName = createTd(data[i].firstName);
			var lastName = createTd(data[i].lastName);
			var address = createTd(data[i].address.address);
			var email = createTd(data[i].email);
			var customerID = createTd(data[i].id);
			var lastUpdate = createTd(data[i].lastUpdate);
			tr.append(caozuo).append(firstName).append(lastName)
					.append(address).append(email).append(customerID).append(
							lastUpdate);
			tbody.append(tr);
		}
	}

	function createTr() {
		return $("<tr></tr>");
	}

	function createTd(data) {
		return $("<td>" + data + "</td>");
	}

});