var clearDateState = false;

$(function () {
    updateGlyphicons();
});

function updateGlyphicons() {
    $('.month_year_picker').datepicker({
        format: "MM yyyy",
        startView: 2,
        minViewMode: 1,
        maxViewMode: 3,
        endDate: '+0d',
        clearBtn: true,
        autoclose: true
    }).on('clearDate', function (selected) {
        clearDateState = true;
        if ($(selected.target).find("input").attr("id").includes("startDate") || $(selected.target).find("input").attr("id").includes("StartDate")) {
            enableEndDate($(selected.target).find("input"), "clearDate");
        }
    }).on('changeDate', function (selected) {
        if (!clearDateState) {
            if ($(selected.target).find("input").attr("id").includes("startDate")) {
                updateDatesLimit(selected, "startDate")
            } else if ($(selected.target).find("input").attr("id").includes("StartDate")) {
                updateDatesLimit(selected, "StartDate")
            } else if ($(selected.target).find("input").attr("id").includes("endDate")) {
                updateDatesLimit(selected, "endDate")
            } else if ($(selected.target).find("input").attr("id").includes("EndDate")) {
                updateDatesLimit(selected, "EndDate")
            }
            $(selected.target).find("input").removeAttr("disabled");
        }

        if ($(selected.target).find("input").attr("id").includes("startDate") || $(selected.target).find("input").attr("id").includes("StartDate")) {
            enableEndDate($(selected.target).find("input"), "changeDate");
        }
    });
    
    
    $('.year_picker').datepicker({
        format: "yyyy",
        startView: 2,
        minViewMode: 2,
        maxViewMode: 3,
        endDate: '+0d',
        clearBtn: true,
        autoclose: true
    });
}


//function to enable end date pickers once start date picker is filled
function enableEndDate(startDatePicker, event) {
    
    var id = startDatePicker.attr("id");
    
    var endDateId;
        
    if (id.includes("startDate") && id.includes("employment")) {
        endDateId = id.replace("startDate", "endDate");
    } else if(id.includes("StartDate") && id.includes("volunteering")) {
        endDateId = id.replace("StartDate", "EndDate");
    }
        
    endDatePicker = document.getElementById(endDateId);
    if (event === "clearDate") {
        endDatePicker.disabled = true;
    } else if (event === "changeDate" && !clearDateState) {
        endDatePicker.disabled = false;
    }

    if (event === "changeDate") {
        clearDateState = false;
    }
    
}

function updateDatesLimit(selected, datePickerType) {
    if (datePickerType === "endDate" || datePickerType === "EndDate") {
        if (datePickerType === "endDate") {
            var startDateId = $(selected.target).find("input").attr("id").replace("endDate", "startDate");
        } else if (datePickerType === "EndDate") {
            var startDateId = $(selected.target).find("input").attr("id").replace("EndDate", "StartDate");
        }

        if (!selected.date.valueOf()) {
            var toEndDate = new Date("+0d");
        } else {
            var toEndDate = new Date(selected.date.valueOf());
        }
        $("input[id='" + startDateId + "']").parent(".month_year_picker").datepicker("setEndDate", toEndDate);
    } else if (datePickerType === "startDate" || datePickerType === "StartDate") {
        if (datePickerType === "startDate") {
            var endDateId = $(selected.target).find("input").attr("id").replace("startDate", "endDate");
        } else if (datePickerType === "StartDate") {
            var endDateId = $(selected.target).find("input").attr("id").replace("StartDate", "EndDate");
        }
        var fromStartDate = new Date(selected.date.valueOf());
        $("input[id='" + endDateId + "']").parent(".month_year_picker").datepicker("setStartDate", fromStartDate);
    }
}