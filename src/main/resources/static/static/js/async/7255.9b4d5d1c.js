/*! For license information please see 7255.9b4d5d1c.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["7255"],{71031:function(e,t,a){"use strict";a.r(t),a.d(t,{default:function(){return b}});var n=a("38956"),o=a("58865");a("57658"),a("15581"),a("34514"),a("2490"),a("41539");var r=a("85893"),u=a("67294"),i=a("71893"),l=a("28685"),s=a("27233"),d=a("87346"),c=a("50533"),f=a("30381"),h=a.n(f);function v(){var e=(0,o._)(["\n  color: ",";\n  margin-bottom: 20px;\n"]);return v=function(){return e},e}function k(){var e=(0,o._)(["\n  width: 100%;\n  padding: 1rem!important;\n"]);return k=function(){return e},e}a("21793");var m=i.default.h3(v(),l.mainColor),w=i.default.div(k()),b=function(){var e=(0,l.getHeaders)().headers,t=(0,n._)((0,u.useState)([]),2),a=t[0],o=t[1],i=(0,n._)((0,u.useState)(null),2),f=i[0],v=i[1],k=(0,c.useSelector)(function(e){var t;return null==e?void 0:null===(t=e.user)||void 0===t?void 0:t.user}),b=(0,n._)((0,u.useState)(!1),2),g=b[0],D=b[1],T=(0,n._)((0,u.useState)({date:new Date,dateFrom:h()(new Date).startOf("isoWeek").toDate(),dateTo:h()(new Date).endOf("isoWeek").toDate(),weekNumber:h()(new Date).isoWeek(),daysOfWeek:function(e,t){h().locale("ru");for(var a=h()().year(t).isoWeek(e).startOf("isoWeek"),n=[],o=0;o<6;o++)n.push(a.clone().add(o,"days").toDate());return n}(h()(new Date).isoWeek(),h()().year())}),2),_=T[0];return T[1],(0,u.useEffect)(function(){D(!0),s.default.get(l.BASE_URL+"/timeTableByWeekOfYear/getTeacherTimeTable?t="+(null==k?void 0:k.id)+"&week="+(null==_?void 0:_.weekNumber)+"&year="+h()(null==_?void 0:_.date).format("YYYY"),{headers:e}).then(function(e){var t,a=null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.obj,n=[],r={};for(var u in null==a||a.forEach(function(e){e.shows.forEach(function(t){var a=t.daysName;!r[a]&&(r[a]={teacherData:e.teacherData,shows:[]}),r[a].shows.push(t)})}),r)n.push(r[u]);console.log(n),o(n),D(!1)}).catch(function(e){console.log(e)}),s.default.get(l.BASE_URL+"/user/getTeacherStatisticsForTimeTableByWeek?teacherId="+(null==k?void 0:k.id)+"&week="+(null==_?void 0:_.weekNumber)+"&year="+h()(null==_?void 0:_.date).format("YYYY"),{headers:e}).then(function(e){var t;v(null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.obj)}).catch(function(e){console.log(e)})},[]),(0,r.jsxs)(w,{children:[(0,r.jsx)(m,{children:"Teacher Time table"}),null!==f&&(0,r.jsx)(d.default,{isLoading:g,obj:_,data:a,statistics:f})]})}}}]);