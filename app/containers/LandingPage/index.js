/**
 *
 *  
 *
 */

import React, { memo, useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { compose } from 'redux';

import { useInjectSaga } from 'utils/injectSaga';
import { useInjectReducer } from 'utils/injectReducer';
import makeSelectLandingPage from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export function LandingPage() {
  useInjectReducer({ key: 'landingPage', reducer });
  useInjectSaga({ key: 'landingPage', saga });

  let chart1 = () => {
    // Themes begin
    am4core.useTheme(am4themes_animated);
    // Themes end

    /**
     * Chart design taken from Samsung health app
     */

    var chart = am4core.create("chartdiv", am4charts.XYChart);
    chart.hiddenState.properties.opacity = 0; // this creates initial fade-in

    chart.data = [{
      "date": "2018-01-01",
      "steps": 4561
    }, {
      "date": "2018-01-02",
      "steps": 5687
    }, {
      "date": "2018-01-03",
      "steps": 6348
    }, {
      "date": "2018-01-04",
      "steps": 4878
    }, {
      "date": "2018-01-05",
      "steps": 9867
    }, {
      "date": "2018-01-06",
      "steps": 7561
    }, {
      "date": "2018-01-07",
      "steps": 1287
    }, {
      "date": "2018-01-08",
      "steps": 3298
    }, {
      "date": "2018-01-09",
      "steps": 5697
    }, {
      "date": "2018-01-10",
      "steps": 4878
    }, {
      "date": "2018-01-11",
      "steps": 8788
    }, {
      "date": "2018-01-12",
      "steps": 9560
    }, {
      "date": "2018-01-13",
      "steps": 11687
    }, {
      "date": "2018-01-14",
      "steps": 5878
    }, {
      "date": "2018-01-15",
      "steps": 9789
    }, {
      "date": "2018-01-16",
      "steps": 3987
    }, {
      "date": "2018-01-17",
      "steps": 5898
    }, {
      "date": "2018-01-18",
      "steps": 9878
    }, {
      "date": "2018-01-19",
      "steps": 13687
    }, {
      "date": "2018-01-20",
      "steps": 6789
    }, {
      "date": "2018-01-21",
      "steps": 4531
    }, {
      "date": "2018-01-22",
      "steps": 5856
    }, {
      "date": "2018-01-23",
      "steps": 5737
    }, {
      "date": "2018-01-24",
      "steps": 9987
    }, {
      "date": "2018-01-25",
      "steps": 16457
    }, {
      "date": "2018-01-26",
      "steps": 7878
    }, {
      "date": "2018-01-27",
      "steps": 6845
    }, {
      "date": "2018-01-28",
      "steps": 4659
    }, {
      "date": "2018-01-29",
      "steps": 7892
    }, {
      "date": "2018-01-30",
      "steps": 7362
    }, {
      "date": "2018-01-31",
      "steps": 3268
    }];

    chart.dateFormatter.inputDateFormat = "YYYY-MM-dd";
    chart.zoomOutButton.disabled = true;

    var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    dateAxis.renderer.grid.template.strokeOpacity = 0;
    dateAxis.renderer.minGridDistance = 10;
    dateAxis.dateFormats.setKey("day", "d");
    dateAxis.tooltip.hiddenState.properties.opacity = 1;
    dateAxis.tooltip.hiddenState.properties.visible = true;


    dateAxis.tooltip.adapter.add("x", function (x, target) {
      return am4core.utils.spritePointToSvg({ x: chart.plotContainer.pixelX, y: 0 }, chart.plotContainer).x + chart.plotContainer.pixelWidth / 2;
    })

    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    valueAxis.renderer.inside = true;
    valueAxis.renderer.labels.template.fillOpacity = 0.3;
    valueAxis.renderer.grid.template.strokeOpacity = 0;
    valueAxis.min = 0;
    valueAxis.cursorTooltipEnabled = false;

    // goal guides
    var axisRange = valueAxis.axisRanges.create();
    axisRange.value = 6000;
    axisRange.grid.strokeOpacity = 0.1;
    axisRange.label.text = "Goal";
    axisRange.label.align = "right";
    axisRange.label.verticalCenter = "bottom";
    axisRange.label.fillOpacity = 0.8;

    valueAxis.renderer.gridContainer.zIndex = 1;

    var axisRange2 = valueAxis.axisRanges.create();
    axisRange2.value = 12000;
    axisRange2.grid.strokeOpacity = 0.1;
    axisRange2.label.text = "2x goal";
    axisRange2.label.align = "right";
    axisRange2.label.verticalCenter = "bottom";
    axisRange2.label.fillOpacity = 0.8;

    var series = chart.series.push(new am4charts.ColumnSeries);
    series.dataFields.valueY = "steps";
    series.dataFields.dateX = "date";
    series.tooltipText = "{valueY.value}";
    series.tooltip.pointerOrientation = "vertical";
    series.tooltip.hiddenState.properties.opacity = 1;
    series.tooltip.hiddenState.properties.visible = true;
    series.tooltip.adapter.add("x", function (x, target) {
      return am4core.utils.spritePointToSvg({ x: chart.plotContainer.pixelX, y: 0 }, chart.plotContainer).x + chart.plotContainer.pixelWidth / 2;
    })

    var columnTemplate = series.columns.template;
    columnTemplate.width = 10;
    columnTemplate.column.cornerRadiusTopLeft = 20;
    columnTemplate.column.cornerRadiusTopRight = 20;
    columnTemplate.strokeOpacity = 0;

    columnTemplate.adapter.add("fill", function (fill, target) {
      var dataItem = target.dataItem;
      if (dataItem.valueY > 6000) {
        return chart.colors.getIndex(0);
      }
      else {
        return am4core.color("#a8b3b7");
      }
    })

    var cursor = new am4charts.XYCursor();
    cursor.behavior = "panX";
    chart.cursor = cursor;
    cursor.lineX.disabled = true;

    chart.events.on("datavalidated", function () {
      dateAxis.zoomToDates(new Date(2018, 0, 21), new Date(2018, 1, 1), false, true);
    });

    var middleLine = chart.plotContainer.createChild(am4core.Line);
    middleLine.strokeOpacity = 1;
    middleLine.stroke = am4core.color("#000000");
    middleLine.strokeDasharray = "2,2";
    middleLine.align = "center";
    middleLine.zIndex = 1;
    middleLine.adapter.add("y2", function (y2, target) {
      return target.parent.pixelHeight;
    })

    cursor.events.on("cursorpositionchanged", updateTooltip);
    dateAxis.events.on("datarangechanged", updateTooltip);

    function updateTooltip() {
      dateAxis.showTooltipAtPosition(0.5);
      series.showTooltipAtPosition(0.5, 0);
      series.tooltip.validate(); // otherwise will show other columns values for a second
    }


    var label = chart.plotContainer.createChild(am4core.Label);
    label.text = "Pan chart to change date";
    label.x = 90;
    label.y = 50;
  }

  let chart2 = () => {

    // Themes begin
    am4core.useTheme(am4themes_animated);
    // Themes end

    // Create chart instance
    var chart = am4core.create("chartdiv1", am4charts.XYChart);

    // Add data
    chart.data = [{
      "date": "2012-07-27",
      "value": 13
    }, {
      "date": "2012-07-28",
      "value": 11
    }, {
      "date": "2012-07-29",
      "value": 15
    }, {
      "date": "2012-07-30",
      "value": 16
    }, {
      "date": "2012-07-31",
      "value": 18
    }, {
      "date": "2012-08-01",
      "value": 13
    }, {
      "date": "2012-08-02",
      "value": 22
    }, {
      "date": "2012-08-03",
      "value": 23
    }, {
      "date": "2012-08-04",
      "value": 20
    }, {
      "date": "2012-08-05",
      "value": 17
    }, {
      "date": "2012-08-06",
      "value": 16
    }, {
      "date": "2012-08-07",
      "value": 18
    }, {
      "date": "2012-08-08",
      "value": 21
    }, {
      "date": "2012-08-09",
      "value": 26
    }, {
      "date": "2012-08-10",
      "value": 24
    }, {
      "date": "2012-08-11",
      "value": 29
    }, {
      "date": "2012-08-12",
      "value": 32
    }, {
      "date": "2012-08-13",
      "value": 18
    }, {
      "date": "2012-08-14",
      "value": 24
    }, {
      "date": "2012-08-15",
      "value": 22
    }, {
      "date": "2012-08-16",
      "value": 18
    }, {
      "date": "2012-08-17",
      "value": 19
    }, {
      "date": "2012-08-18",
      "value": 14
    }, {
      "date": "2012-08-19",
      "value": 15
    }, {
      "date": "2012-08-20",
      "value": 12
    }, {
      "date": "2012-08-21",
      "value": 8
    }, {
      "date": "2012-08-22",
      "value": 9
    }, {
      "date": "2012-08-23",
      "value": 8
    }, {
      "date": "2012-08-24",
      "value": 7
    }, {
      "date": "2012-08-25",
      "value": 5
    }, {
      "date": "2012-08-26",
      "value": 11
    }, {
      "date": "2012-08-27",
      "value": 13
    }, {
      "date": "2012-08-28",
      "value": 18
    }, {
      "date": "2012-08-29",
      "value": 20
    }, {
      "date": "2012-08-30",
      "value": 29
    }, {
      "date": "2012-08-31",
      "value": 33
    }, {
      "date": "2012-09-01",
      "value": 42
    }, {
      "date": "2012-09-02",
      "value": 35
    }, {
      "date": "2012-09-03",
      "value": 31
    }, {
      "date": "2012-09-04",
      "value": 47
    }, {
      "date": "2012-09-05",
      "value": 52
    }, {
      "date": "2012-09-06",
      "value": 46
    }, {
      "date": "2012-09-07",
      "value": 41
    }, {
      "date": "2012-09-08",
      "value": 43
    }, {
      "date": "2012-09-09",
      "value": 40
    }, {
      "date": "2012-09-10",
      "value": 39
    }, {
      "date": "2012-09-11",
      "value": 34
    }, {
      "date": "2012-09-12",
      "value": 29
    }, {
      "date": "2012-09-13",
      "value": 34
    }, {
      "date": "2012-09-14",
      "value": 37
    }, {
      "date": "2012-09-15",
      "value": 42
    }, {
      "date": "2012-09-16",
      "value": 49
    }, {
      "date": "2012-09-17",
      "value": 46
    }, {
      "date": "2012-09-18",
      "value": 47
    }, {
      "date": "2012-09-19",
      "value": 55
    }, {
      "date": "2012-09-20",
      "value": 59
    }, {
      "date": "2012-09-21",
      "value": 58
    }, {
      "date": "2012-09-22",
      "value": 57
    }, {
      "date": "2012-09-23",
      "value": 61
    }, {
      "date": "2012-09-24",
      "value": 59
    }, {
      "date": "2012-09-25",
      "value": 67
    }, {
      "date": "2012-09-26",
      "value": 65
    }, {
      "date": "2012-09-27",
      "value": 61
    }, {
      "date": "2012-09-28",
      "value": 66
    }, {
      "date": "2012-09-29",
      "value": 69
    }, {
      "date": "2012-09-30",
      "value": 71
    }, {
      "date": "2012-10-01",
      "value": 67
    }, {
      "date": "2012-10-02",
      "value": 63
    }, {
      "date": "2012-10-03",
      "value": 46
    }, {
      "date": "2012-10-04",
      "value": 32
    }, {
      "date": "2012-10-05",
      "value": 21
    }, {
      "date": "2012-10-06",
      "value": 18
    }, {
      "date": "2012-10-07",
      "value": 21
    }, {
      "date": "2012-10-08",
      "value": 28
    }, {
      "date": "2012-10-09",
      "value": 27
    }, {
      "date": "2012-10-10",
      "value": 36
    }, {
      "date": "2012-10-11",
      "value": 33
    }, {
      "date": "2012-10-12",
      "value": 31
    }, {
      "date": "2012-10-13",
      "value": 30
    }, {
      "date": "2012-10-14",
      "value": 34
    }, {
      "date": "2012-10-15",
      "value": 38
    }, {
      "date": "2012-10-16",
      "value": 37
    }, {
      "date": "2012-10-17",
      "value": 44
    }, {
      "date": "2012-10-18",
      "value": 49
    }, {
      "date": "2012-10-19",
      "value": 53
    }, {
      "date": "2012-10-20",
      "value": 57
    }, {
      "date": "2012-10-21",
      "value": 60
    }, {
      "date": "2012-10-22",
      "value": 61
    }, {
      "date": "2012-10-23",
      "value": 69
    }, {
      "date": "2012-10-24",
      "value": 67
    }, {
      "date": "2012-10-25",
      "value": 72
    }, {
      "date": "2012-10-26",
      "value": 77
    }, {
      "date": "2012-10-27",
      "value": 75
    }, {
      "date": "2012-10-28",
      "value": 70
    }, {
      "date": "2012-10-29",
      "value": 72
    }, {
      "date": "2012-10-30",
      "value": 70
    }, {
      "date": "2012-10-31",
      "value": 72
    }, {
      "date": "2012-11-01",
      "value": 73
    }, {
      "date": "2012-11-02",
      "value": 67
    }, {
      "date": "2012-11-03",
      "value": 68
    }, {
      "date": "2012-11-04",
      "value": 65
    }, {
      "date": "2012-11-05",
      "value": 71
    }, {
      "date": "2012-11-06",
      "value": 75
    }, {
      "date": "2012-11-07",
      "value": 74
    }, {
      "date": "2012-11-08",
      "value": 71
    }, {
      "date": "2012-11-09",
      "value": 76
    }, {
      "date": "2012-11-10",
      "value": 77
    }, {
      "date": "2012-11-11",
      "value": 81
    }, {
      "date": "2012-11-12",
      "value": 83
    }, {
      "date": "2012-11-13",
      "value": 80
    }, {
      "date": "2012-11-14",
      "value": 81
    }, {
      "date": "2012-11-15",
      "value": 87
    }, {
      "date": "2012-11-16",
      "value": 82
    }, {
      "date": "2012-11-17",
      "value": 86
    }, {
      "date": "2012-11-18",
      "value": 80
    }, {
      "date": "2012-11-19",
      "value": 87
    }, {
      "date": "2012-11-20",
      "value": 83
    }, {
      "date": "2012-11-21",
      "value": 85
    }, {
      "date": "2012-11-22",
      "value": 84
    }, {
      "date": "2012-11-23",
      "value": 82
    }, {
      "date": "2012-11-24",
      "value": 73
    }, {
      "date": "2012-11-25",
      "value": 71
    }, {
      "date": "2012-11-26",
      "value": 75
    }, {
      "date": "2012-11-27",
      "value": 79
    }, {
      "date": "2012-11-28",
      "value": 70
    }, {
      "date": "2012-11-29",
      "value": 73
    }, {
      "date": "2012-11-30",
      "value": 61
    }, {
      "date": "2012-12-01",
      "value": 62
    }, {
      "date": "2012-12-02",
      "value": 66
    }, {
      "date": "2012-12-03",
      "value": 65
    }, {
      "date": "2012-12-04",
      "value": 73
    }, {
      "date": "2012-12-05",
      "value": 79
    }, {
      "date": "2012-12-06",
      "value": 78
    }, {
      "date": "2012-12-07",
      "value": 78
    }, {
      "date": "2012-12-08",
      "value": 78
    }, {
      "date": "2012-12-09",
      "value": 74
    }, {
      "date": "2012-12-10",
      "value": 73
    }, {
      "date": "2012-12-11",
      "value": 75
    }, {
      "date": "2012-12-12",
      "value": 70
    }, {
      "date": "2012-12-13",
      "value": 77
    }, {
      "date": "2012-12-14",
      "value": 67
    }, {
      "date": "2012-12-15",
      "value": 62
    }, {
      "date": "2012-12-16",
      "value": 64
    }, {
      "date": "2012-12-17",
      "value": 61
    }, {
      "date": "2012-12-18",
      "value": 59
    }, {
      "date": "2012-12-19",
      "value": 53
    }, {
      "date": "2012-12-20",
      "value": 54
    }, {
      "date": "2012-12-21",
      "value": 56
    }, {
      "date": "2012-12-22",
      "value": 59
    }, {
      "date": "2012-12-23",
      "value": 58
    }, {
      "date": "2012-12-24",
      "value": 55
    }, {
      "date": "2012-12-25",
      "value": 52
    }, {
      "date": "2012-12-26",
      "value": 54
    }, {
      "date": "2012-12-27",
      "value": 50
    }, {
      "date": "2012-12-28",
      "value": 50
    }, {
      "date": "2012-12-29",
      "value": 51
    }, {
      "date": "2012-12-30",
      "value": 52
    }, {
      "date": "2012-12-31",
      "value": 58
    }, {
      "date": "2013-01-01",
      "value": 60
    }, {
      "date": "2013-01-02",
      "value": 67
    }, {
      "date": "2013-01-03",
      "value": 64
    }, {
      "date": "2013-01-04",
      "value": 66
    }, {
      "date": "2013-01-05",
      "value": 60
    }, {
      "date": "2013-01-06",
      "value": 63
    }, {
      "date": "2013-01-07",
      "value": 61
    }, {
      "date": "2013-01-08",
      "value": 60
    }, {
      "date": "2013-01-09",
      "value": 65
    }, {
      "date": "2013-01-10",
      "value": 75
    }, {
      "date": "2013-01-11",
      "value": 77
    }, {
      "date": "2013-01-12",
      "value": 78
    }, {
      "date": "2013-01-13",
      "value": 70
    }, {
      "date": "2013-01-14",
      "value": 70
    }, {
      "date": "2013-01-15",
      "value": 73
    }, {
      "date": "2013-01-16",
      "value": 71
    }, {
      "date": "2013-01-17",
      "value": 74
    }, {
      "date": "2013-01-18",
      "value": 78
    }, {
      "date": "2013-01-19",
      "value": 85
    }, {
      "date": "2013-01-20",
      "value": 82
    }, {
      "date": "2013-01-21",
      "value": 83
    }, {
      "date": "2013-01-22",
      "value": 88
    }, {
      "date": "2013-01-23",
      "value": 85
    }, {
      "date": "2013-01-24",
      "value": 85
    }, {
      "date": "2013-01-25",
      "value": 80
    }, {
      "date": "2013-01-26",
      "value": 87
    }, {
      "date": "2013-01-27",
      "value": 84
    }, {
      "date": "2013-01-28",
      "value": 83
    }, {
      "date": "2013-01-29",
      "value": 84
    }, {
      "date": "2013-01-30",
      "value": 81
    }];

    // Set input format for the dates
    chart.dateFormatter.inputDateFormat = "yyyy-MM-dd";

    // Create axes
    var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());

    // Create series
    var series = chart.series.push(new am4charts.LineSeries());
    series.dataFields.valueY = "value";
    series.dataFields.dateX = "date";
    series.tooltipText = "{value}"
    series.strokeWidth = 2;
    series.minBulletDistance = 15;

    // Drop-shaped tooltips
    series.tooltip.background.cornerRadius = 20;
    series.tooltip.background.strokeOpacity = 0;
    series.tooltip.pointerOrientation = "vertical";
    series.tooltip.label.minWidth = 40;
    series.tooltip.label.minHeight = 40;
    series.tooltip.label.textAlign = "middle";
    series.tooltip.label.textValign = "middle";

    // Make bullets grow on hover
    var bullet = series.bullets.push(new am4charts.CircleBullet());
    bullet.circle.strokeWidth = 2;
    bullet.circle.radius = 4;
    bullet.circle.fill = am4core.color("#fff");

    var bullethover = bullet.states.create("hover");
    bullethover.properties.scale = 1.3;

    // Make a panning cursor
    chart.cursor = new am4charts.XYCursor();
    chart.cursor.behavior = "panXY";
    chart.cursor.xAxis = dateAxis;
    chart.cursor.snapToSeries = series;

    // Create vertical scrollbar and place it before the value axis
    // chart.scrollbarY = new am4core.Scrollbar();
    // chart.scrollbarY.parent = chart.leftAxesContainer;
    // chart.scrollbarY.toBack();

    // Create a horizontal scrollbar with previe and place it underneath the date axis
    // chart.scrollbarX = new am4charts.XYChartScrollbar();
    // chart.scrollbarX.series.push(series);
    // chart.scrollbarX.parent = chart.bottomAxesContainer;

    dateAxis.start = 0.79;
    dateAxis.keepSelection = true;

  }
  let chart3 = () => {


    // Themes begin
    am4core.useTheme(am4themes_animated);
    // Themes end

    // Create chart instance
    var chart = am4core.create("chartdiv2", am4charts.PieChart);

    // Add data
    chart.data = [{
      "country": "Lithuania",
      "litres": 501.9
    }, {
      "country": "Czech Republic",
      "litres": 301.9
    }, {
      "country": "Ireland",
      "litres": 201.1
    }, {
      "country": "Germany",
      "litres": 165.8
    }, {
      "country": "Australia",
      "litres": 139.9
    }, {
      "country": "Austria",
      "litres": 128.3
    }, {
      "country": "UK",
      "litres": 99
    }, {
      "country": "Belgium",
      "litres": 60
    }, {
      "country": "The Netherlands",
      "litres": 50
    }];

    // Add and configure Series
    var pieSeries = chart.series.push(new am4charts.PieSeries());
    pieSeries.dataFields.value = "litres";
    pieSeries.dataFields.category = "country";
    pieSeries.innerRadius = am4core.percent(50);
    pieSeries.ticks.template.disabled = true;
    pieSeries.labels.template.disabled = true;

    var rgm = new am4core.RadialGradientModifier();
    rgm.brightnesses.push(-0.8, -0.8, -0.5, 0, - 0.5);
    pieSeries.slices.template.fillModifier = rgm;
    pieSeries.slices.template.strokeModifier = rgm;
    pieSeries.slices.template.strokeOpacity = 0.4;
    pieSeries.slices.template.strokeWidth = 0;

    chart.legend = new am4charts.Legend();
    chart.legend.position = "right";
    chart.legend.disabled = true;
  }

  useEffect(() => {
    chart1();
    chart2();
    chart3();
  }, [])

  function refreshPage() {
    window.location.reload(false);
  }

  return (
    <React.Fragment>
      <Helmet>
        <title>LandingPage</title>
        <meta name="description" content="Description of LandingPage" />
      </Helmet>

      <header className="header">
        <div className="d-flex">
          <div className="flex-30">
            <h6>Dashboard</h6>
          </div>
          <div className="flex-70">
            <div className="header-group">
              <div className="button-group">
                <button className="btn btn-primary" onClick={refreshPage}>
                  <i className="fas fa-retweet"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </header>

      <div className="content-body">
        <div className="d-flex">
          <div className="flex-30 pd-r-5">
            <ul className="list-style-none counter-list">
              <li className="pd-t-0">
                <div className="counter-list-wrapper">
                  <p>Customers</p>
                  <h5>36,254</h5>
                  <div className="counter-item">
                    <i className="fas fa-arrow-up text-success"></i>
                    <p className="text-success">5.27%</p>
                  </div>
                  <h6>Since last month</h6>
                </div>
              </li>
              <li className="pd-t-0">
                <div className="counter-list-wrapper">
                  <p>Orders</p>
                  <h5>5,543</h5>
                  <div className="counter-item">
                    <i className="fas fa-arrow-down text-danger"></i>
                    <p className="text-danger">1.08%</p>
                  </div>
                  <h6>Since last month</h6>
                </div>
              </li>
              <li>
                <div className="counter-list-wrapper">
                  <p>Revenue</p>
                  <h5>Rs 6,254</h5>
                  <div className="counter-item">
                    <i className="fas fa-arrow-down text-danger"></i>
                    <p className="text-danger">7.00%</p>
                  </div>
                  <h6>Since last month</h6>
                </div>
              </li>
              <li>
                <div className="counter-list-wrapper">
                  <p>Growth</p>
                  <h5>+ 30.56%</h5>
                  <div className="counter-item">
                    <i className="fas fa-arrow-up text-success"></i>
                    <p className="text-success">4.87%</p>
                  </div>
                  <h6>Since last month</h6>
                </div>
              </li>
            </ul>
          </div>
          <div className="flex-70 pd-l-5">
            <div className="card">
              <div className="card-header">
                <h6>PROJECTIONS VS ACTUALS</h6>
              </div>
              <div className="card-body">
                <div className="chart-wrapper" id="chartdiv"></div>
              </div>
            </div>
          </div>
          <div className="flex-70 pd-r-5">
            <div className="card">
              <div className="card-header">
                <h6>TOTAL SALES</h6>
              </div>
              <div className="card-body">
                <div className="chart-wrapper" id="chartdiv1"></div>
              </div>
            </div>
          </div>
          <div className="flex-30 pd-l-5">
            <div className="card">
              <div className="card-header">
                <h6>TOTAL SALES</h6>
              </div>
              <div className="card-body">
                <div className="chart-wrapper" id="chartdiv2"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
}

LandingPage.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  landingPage: makeSelectLandingPage(),
});

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

const withConnect = connect(
  mapStateToProps,
  mapDispatchToProps,
);

export default compose(
  withConnect,
  memo,
)(LandingPage);
