/*! For license information please see 7180.cc2f7ccb.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["7180"],{41405:function(e,t,n){"use strict";n.r(t),n.d(t,{default:function(){return w}});var a=n("38956"),r=n("58865"),u=n("85893"),i=n("67294"),o=n("50533"),d=n("71893"),l=n("31121"),s=n("30381"),f=n.n(s),c=n("71632"),v=n("52861");function k(){var e=(0,r._)(["\n\n  padding: 10px 0;\n  display: flex;\n  flex-wrap: wrap;\n  align-items: center;\n  gap: 25px;\n  \n  ","\n"]);return k=function(){return e},e}function p(){var e=(0,r._)(["\n width: 100%;\n  padding: 1rem;\n"]);return p=function(){return e},e}var x=d.default.div(k(),(0,c.extrasmall)({margin:0,justifyContent:"center"})),m=d.default.div(p()),w=function(){var e,t=(0,o.useSelector)(function(e){var t;return null==e?void 0:null===(t=e.student)||void 0===t?void 0:t.student}),n=(0,a._)((0,i.useState)({date:f()().format("YYYY-[W]WW"),dateFrom:f()().startOf("isoWeek").toDate(),dateTo:f()().endOf("isoWeek").toDate(),weekNumber:f()().isoWeek()}),2),r=n[0],d=n[1];return(0,u.jsxs)(m,{children:[(0,u.jsx)(x,{children:(0,u.jsx)(v.default,{label:"Date of the week",type:"week",sx:{width:"200px"},value:r.date,onChange:function(e){var t=e.target.value,n=f()(t).isoWeek(),a=f()(t).startOf("isoWeek").toDate(),r=f()(t).endOf("isoWeek").toDate();d({date:t,dateFrom:a,dateTo:r,weekNumber:n})}})}),(0,u.jsx)(l.default,{userId:null==t?void 0:t.id,group:null==t?void 0:null===(e=t.groupData)||void 0===e?void 0:e.name,s:!1,obj:r})]})}}}]);