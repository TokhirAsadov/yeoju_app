/*! For license information please see 8775.34dd407e.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["8775"],{77918:function(e,l,t){"use strict";t.r(l);var n=t("58865");t("33948"),t("25387"),t("72608"),t("2490"),t("15581"),t("34514"),t("57658");var o=t("85893"),i=t("67294"),r=t("71893"),a=t("16393"),d=t("27233"),s=t("50533"),u=t("30381"),c=t.n(u);t("21793");var v=t("67087"),h=t("52861");function f(){let e=(0,n._)(["\n    color: ",";\n    margin-bottom: 20px;\n"]);return f=function(){return e},e}function m(){let e=(0,n._)(["\n    width: 100%;\n    padding: 1rem !important;\n"]);return m=function(){return e},e}let x=r.default.h3(f(),a.mainColor),p=r.default.div(m());l.default=()=>{let{headers:e}=(0,a.getHeaders)(),l=(0,s.useSelector)(e=>{var l;return null==e?void 0:null===(l=e.user)||void 0===l?void 0:l.user}),[t,n]=(0,i.useState)([]),[r,u]=(0,i.useState)(null),[f,m]=(0,i.useState)(!1),[b,g]=(0,i.useState)(c()(a.serverTimeStorage));return(0,i.useEffect)(()=>{c().locale("ru"),(async()=>{try{var t,o;m(!0);let[i,r]=await Promise.all([d.default.get("".concat(a.BASE_URL,"/timeTableByWeekOfYear/getTeacherTimeTable?t=").concat(null==l?void 0:l.id,"&week=").concat(null==b?void 0:b.isoWeek(),"&year=").concat(null==b?void 0:b.year()),{headers:e}),d.default.get("".concat(a.BASE_URL,"/user/getTeacherStatisticsForTimeTableByWeek?teacherId=").concat(null==l?void 0:l.id,"&week=").concat(null==b?void 0:b.isoWeek(),"&year=").concat(null==b?void 0:b.year()),{headers:e})]),s=((null==i?void 0:null===(t=i.data)||void 0===t?void 0:t.obj)||[]).reduce((e,l)=>(l.shows.forEach(t=>{let n=t.daysName;!e[n]&&(e[n]={teacherData:l.teacherData,shows:[]}),e[n].shows.push(t)}),e),{});n(Object.values(s)),u((null==r?void 0:null===(o=r.data)||void 0===o?void 0:o.obj)||[])}catch(e){u([])}finally{m(!1)}})()},[b]),(0,o.jsxs)(p,{children:[(0,o.jsx)(x,{children:"Schedule of lessons"}),(0,o.jsx)(h.default,{label:"Date of the week",type:"week",sx:{width:"200px"},value:b.format("YYYY-[W]WW"),onChange:e=>g(c()(e.target.value)),size:"small"}),(0,o.jsx)(v.default,{isLoading:f,dateInput:b,data:t,statistics:r})]})}},67087:function(e,l,t){"use strict";t.r(l);var n=t("58865");t("57640"),t("9924"),t("31672"),t("59461"),t("2490"),t("88449"),t("19894");var o=t("85893");t("67294");var i=t("71893"),r=t("7104"),a=t("16393"),d=t("30381"),s=t.n(d),u=t("89589"),c=t("78951"),v=t("50533"),h=t("55693");function f(){let e=(0,n._)(["\n    width: 100%;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    flex-direction: column;\n    gap: 5px;\n"]);return f=function(){return e},e}function m(){let e=(0,n._)(["\n    width: 100%;\n    overflow: scroll;\n"]);return m=function(){return e},e}let x=i.default.span(f()),p=i.default.div(m());l.default=e=>{var l;let{data:t,statistics:n,dateInput:i,isLoading:d}=e,f=(0,v.useSelector)(e=>e.hourSection),m=null===(l=(e=>{let l=e.startOf("isoWeek");return Array.from({length:6},(e,t)=>l.clone().add(t,"days").toDate())})(i))||void 0===l?void 0:l.map((e,l)=>{let t=s()(e).format("dd"),n=s()(e).format("dddd"),o=t.charAt(0).toUpperCase()+t.slice(1).toLowerCase();return{day:l+1,name:o,name2:n.charAt(0).toUpperCase()+n.slice(1).toLowerCase(),date:s()(e)}}),b=(e,l,t,n)=>{let i=s()(n).set({hour:s()(t).hours(),minute:s()(t).minutes()});if(s()(a.serverTimeStorage).isAfter(i)){var d;if(!e||!l)return(0,o.jsx)(r.Chip,{label:(0,o.jsx)("p",{style:{fontSize:11},children:"Missed"}),color:"error",size:"small"});let t=s()(e),n=s()(null===(d=f.find(e=>e.number===l))||void 0===d?void 0:d.start).date(t.date()).diff(t,"milliseconds"),i=s().utc(Math.abs(n)).format("mm:ss");return n<0?(0,o.jsx)(r.Tooltip,{title:t.format("HH:mm"),arrow:!0,placement:"top",children:(0,o.jsx)(r.Chip,{label:(0,o.jsx)("p",{style:{fontSize:11},children:i}),color:"warning",size:"small"})}):n>0?(0,o.jsx)(r.Tooltip,{title:t.format("HH:mm"),arrow:!0,placement:"top",children:(0,o.jsx)(r.Chip,{label:(0,o.jsx)("p",{style:{fontSize:11},children:i}),color:"success",size:"small"})}):(0,o.jsx)(r.Chip,{label:"On time",color:"default",size:"small"})}};return(0,o.jsx)(p,{children:d?(0,o.jsx)(r.Stack,{spacing:1,children:(0,o.jsx)(r.Skeleton,{variant:"rectangular",style:{margin:"15px auto"},width:"100%",height:500})}):(null==t?void 0:t.length)===0?(0,o.jsx)(r.Card,{children:(0,o.jsxs)(r.CardContent,{children:[(0,o.jsx)(u.default,{textAlign:"center",fontSize:20,fontWeight:"bold",color:"error",children:"No class schedule available !"}),(0,o.jsx)(c.default,{w:200,h:180})]})}):(0,o.jsx)(r.TableContainer,{component:r.Paper,style:{marginTop:20,overflow:"auto"},children:(0,o.jsxs)(r.Table,{sx:{minWidth:2200},children:[(0,o.jsx)(r.TableHead,{children:(0,o.jsxs)(r.TableRow,{children:[(0,o.jsx)(r.TableCell,{sx:{position:"sticky",left:0,backgroundColor:"#fff",zIndex:1,borderRight:"1px solid rgba(224, 224, 224, 1)"},align:"center",children:(0,o.jsx)("strong",{children:"TIME"})}),null==f?void 0:f.map((e,l)=>(0,o.jsx)(r.TableCell,{sx:{width:200,borderRight:"1px solid rgba(224, 224, 224, 1)"},align:"center",children:(0,o.jsx)("strong",{children:null==e?void 0:e.hour})},l))]})}),(0,o.jsx)(r.TableBody,{children:null==m?void 0:m.map((e,l)=>{let i=null==t?void 0:t.find(l=>{var t,n;return(null==l?void 0:null===(t=l.shows[0])||void 0===t?void 0:t.daysName)===(null==e?void 0:e.name)||(null==l?void 0:null===(n=l.shows[0])||void 0===n?void 0:n.daysName)===(null==e?void 0:e.name2)}),a=null;return n&&(a=n[null==e?void 0:e.day]),(0,o.jsxs)(r.TableRow,{children:[(0,o.jsx)(r.TableCell,{sx:{position:"sticky",left:0,backgroundColor:"#fff",zIndex:1,height:120,borderRight:"1px solid rgba(224, 224, 224, 1)"},align:"center",children:(0,o.jsx)(u.default,{fontSize:12,children:(0,o.jsxs)("b",{children:[null==e?void 0:e.name,(0,o.jsx)("br",{})," ",null==e?void 0:e.date.format("DD.MM.YYYY")]})})}),null==f?void 0:f.map((l,t)=>{var n,d,s,c,v,f,m;let p=null==i?void 0:null===(n=i.shows)||void 0===n?void 0:n.find(e=>(null==e?void 0:e.hourNumber)===(null==l?void 0:l.number)),g=(null==a?void 0:a.find(e=>{var l;return null==e?void 0:null===(l=e.room)||void 0===l?void 0:l.startsWith(null==p?void 0:p.room)}))||(null==a?void 0:a.find(e=>{var l,t,n;return null==e?void 0:null===(n=e.room)||void 0===n?void 0:n.startsWith(null==p?void 0:null===(t=p.room)||void 0===t?void 0:t.substring(0,(null==p?void 0:null===(l=p.room)||void 0===l?void 0:l.indexOf("-"))+4))})),j=null==g?void 0:null===(s=g.times)||void 0===s?void 0:null===(d=s.filter(e=>(null==e?void 0:e.section)===(null==l?void 0:l.number)))||void 0===d?void 0:d.sort((e,l)=>(null==e?void 0:e.time)-(null==l?void 0:l.time))[0];return p?(0,o.jsxs)(r.TableCell,{align:"center",sx:{position:"relative",borderRight:"1px solid rgba(224, 224, 224, 1)"},children:[(0,o.jsx)(u.default,{sx:{position:"absolute",top:5,left:"50%",transform:"translateX(-50%)"},fontSize:14,fontWeight:"bold",children:null==p?void 0:null===(c=p.room)||void 0===c?void 0:c.substring(0,5)}),(0,o.jsx)(h.default,{sx:{position:"absolute",bottom:5,left:5},children:(0,o.jsx)(r.Tooltip,{title:(null==p?void 0:null===(v=p.groups)||void 0===v?void 0:v.length)>1&&(null==p?void 0:null===(f=p.groups)||void 0===f?void 0:f.join(",")),arrow:!0,placement:"top",children:(0,o.jsx)(r.Chip,{label:(0,o.jsxs)("p",{style:{fontSize:10,fontWeight:"bold"},children:[null==p?void 0:p.groups[0],(null==p?void 0:null===(m=p.groups)||void 0===m?void 0:m.length)>1&&"..."]}),color:"default",size:"small"})})}),(0,o.jsx)(x,{children:(0,o.jsx)(u.default,{fontSize:10,children:p.lessonName})}),(0,o.jsx)(h.default,{sx:{position:"absolute",bottom:5,right:5},children:b(null==j?void 0:j.time,null==j?void 0:j.section,l.start,null==e?void 0:e.date)})]},t):(0,o.jsx)(r.TableCell,{align:"center",sx:{borderRight:"1px solid rgba(224, 224, 224, 1)"},children:" - "},t)})]},l)})})]})})})}}}]);