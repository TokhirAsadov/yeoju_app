/*! For license information please see 9156.b99be70c.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["9156"],{9592:function(e,n,l){"use strict";l.r(n);var t=l("58865");l("33948"),l("31672"),l("59461"),l("2490"),l("57640"),l("9924");var i=l("85893"),d=l("67294"),a=l("55693"),r=l("42154"),o=l("32392"),u=l("44025"),s=l("61261"),c=l("52861"),h=l("97367"),f=l("79352"),x=l("7192"),v=l("71893"),p=l("57024"),m=l("16393"),j=l("38855"),b=l("50533"),g=l("27233"),S=l("92120"),w=l("27120"),k=l("30381"),y=l.n(k),C=l("78258"),Y=l("7104"),M=l("8193"),F=l("78951"),I=l("3506"),D=l("37426"),_=l("55887"),H=l("54494");function R(){let e=(0,t._)(["\n    margin-top: 25px;\n    width: 100%;\n    overflow-x: scroll;\n\n    table {\n        min-width: 700px;\n        border-collapse: collapse;\n        width: 100%;\n        border-radius: 5px;\n        overflow: hidden;\n\n        td, th {\n            border: 1px solid #ddd;\n            padding: 8px;\n            font-size: 15px;\n        }\n\n        th {\n            text-align: center;\n        }\n\n        tr {\n            &:nth-child(even) {\n                background-color: #fcf9f9;\n            }\n        }\n\n        th {\n            background-color: ",";\n            color: white;\n        }\n    }\n\n"]);return R=function(){return e},e}function T(){let e=(0,t._)(["\n    ","\n"]);return T=function(){return e},e}function E(){let e=(0,t._)(["\n    width: 30px;\n    height: 30px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    background-color: #ffffff;\n    border-radius: 50%;\n    color: ",";\n    font-size: 26px;\n    border: none;\n    position: absolute;\n    top: 10px;\n    right: 10px;\n    z-index: 10;\n"]);return E=function(){return e},e}function A(){let e=(0,t._)(["\n    width: 40px;\n    height: 40px;\n    border-radius: 5px;\n    border: 1px solid #a6a6e0;\n    display: flex;\n    justify-content: center;\n    align-items: center;\n    cursor: pointer;\n    margin: 0 auto;\n    color: ",";\n\n    &:hover {\n        background-color: rgba(0, 0, 0, 0.2);\n    }\n"]);return A=function(){return e},e}function U(){let e=(0,t._)(["\n    margin-top: 15px;\n    padding: 15px;\n    display: flex;\n    flex-wrap: wrap;\n    gap: 30px;\n    ","\n"]);return U=function(){return e},e}function W(){let e=(0,t._)(["\n    padding: 10px;\n"]);return W=function(){return e},e}let L=v.default.div(R(),m.mainColor),z={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"60vw",bgcolor:"background.paper",border:"none",borderRadius:2,boxShadow:24},B={position:"absolute",top:"50%",left:"50%",overflow:"hidden",transform:"translate(-50%, -50%)",width:"350px",bgcolor:"background.paper",border:"none",borderRadius:2,boxShadow:24},K={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:600,bgcolor:"background.paper",border:"none",borderRadius:2,boxShadow:24},N=v.default.div(T(),(0,p.extrasmall)({width:"97% !important"})),G=v.default.button(E(),m.mainColor),P=v.default.div(A(),e=>e.color?e.color:"#000"),O=v.default.div(U(),(0,p.extrasmall)({justifyContent:"center"})),Q=v.default.div(W());n.default=()=>{var e,n;let[l,t]=(0,d.useState)(""),[v,p]=(0,d.useState)(""),[k,R]=(0,d.useState)(!1),[T,E]=(0,d.useState)(!1),[A,U]=(0,d.useState)(!1),[W,Z]=(0,d.useState)(null),[V,q]=(0,d.useState)(y()(m.serverTimeStorage).format("")),J=(0,b.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.educationYear)||void 0===n?void 0:n.educationYear})||null,[X,$]=(0,d.useState)([]),[ee,en]=(0,d.useState)([]),[el,et]=(0,d.useState)([]),ei=(0,b.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.educationYearStatistics)||void 0===n?void 0:n.educationYearStatisticsLoadingStatus})||!1,ed=(0,b.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.educationYearStatistics)||void 0===n?void 0:n.educationYearStatistics})||[],[ea,er]=(0,d.useState)(null),[eo,eu]=(0,d.useState)(null),{headers:es}=(0,m.getHeaders)(),ec=(0,b.useDispatch)(),[eh,ef]=(0,d.useState)(null),[ex,ev]=(0,d.useState)(!0),ep=(0,b.useSelector)(e=>e.hourSection),em=e=>{Z(e),R(!0)},ej=()=>{R(!1),Z(null)};(0,d.useEffect)(()=>{eb(),eg()},[]);let eb=async()=>{await g.default.get(m.BASE_URL+"/education/educationYearsForSelected",{headers:es}).then(e=>{var n,l,t;$(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.obj),ec((0,S.fetchEducationYear)(null==e?void 0:null===(t=e.data)||void 0===t?void 0:null===(l=t.obj)||void 0===l?void 0:l[0]))}).catch(e=>{})};(0,d.useEffect)(()=>{var e;l&&ea&&eo&&g.default.get(m.BASE_URL+"/dekan/getGroupsNamesForRektorByFacultyId/".concat(null==el?void 0:null===(e=el.find(e=>(null==e?void 0:e.name)===l))||void 0===e?void 0:e.id,"?course=").concat(eo,"&eduType=").concat(ea),{headers:es}).then(e=>{var n,l,t;en(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.sort(function(e,n){return(null==e?void 0:e.name)>(null==n?void 0:n.name)?1:(null==e?void 0:e.name)<(null==n?void 0:n.name)?-1:0})),p(null==e?void 0:null===(t=e.data)||void 0===t?void 0:null===(l=t.obj[0])||void 0===l?void 0:l.name)}).catch(e=>{})},[l,ea,eo]),(0,d.useEffect)(()=>{var e;v&&ec((0,w.educationYearStatisticsFetching)());let n=y()(V);v&&g.default.get(m.BASE_URL+"/group/getStudentStatisticsForDeanOneWeek/".concat(null===(e=ee.find(e=>e.name===v))||void 0===e?void 0:e.id,"?educationYearId=").concat(null==J?void 0:J.id,"&weekday=").concat(n.day(),"&week=").concat(n.isoWeek(),"&year=").concat(n.year()),{headers:es}).then(e=>{var n;ec((0,w.educationYearStatisticsFetched)(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.obj.sort(eS))),ev(!0)}).catch(e=>{ev(!0)})},[v,V]);let eg=()=>{g.default.get("".concat(m.BASE_URL,"/faculty/allFacultiesWithShortName"),{headers:es}).then(e=>{var n;et(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.obj)}).catch(e=>void 0)};function eS(e,n){let l=null==e?void 0:e.fullName.toUpperCase(),t=null==n?void 0:n.fullName.toUpperCase();return l<t?-1:l>t?1:0}let ew=e=>{var n;if(!e.year||!e.week||!e.weekday||!e.section)return!1;let l=y()(m.serverTimeStorage).format("DD.MM.YYYY"),t=y()(m.serverTimeStorage).format("HH:mm"),i=null===(n=ep.find(e=>{let n=y()(e.start).format("HH:mm"),l=y()(e.end).format("HH:mm");return y()(t,"HH:mm").isBetween(y()(n,"HH:mm"),y()(l,"HH:mm"),null,"[]")}))||void 0===n?void 0:n.number;return l===y()(m.serverTimeStorage).year(e.year).week(e.week).isoWeekday(e.weekday).format("DD.MM.YYYY")&&e.section===i};return(0,i.jsxs)(Q,{children:[(0,i.jsxs)(O,{children:[(0,i.jsx)(a.default,{sx:{width:200},children:(0,i.jsxs)(r.default,{fullWidth:!0,children:[(0,i.jsx)(o.default,{id:"semestr",children:"Semestr"}),(0,i.jsx)(u.default,{labelId:"semestr",id:"demo-simple-select",value:null==J?void 0:J.name,label:"Semistr",onChange:e=>{e.target.value!==(null==J?void 0:J.name)&&ec((0,w.educationYearStatisticsFetching)()),ec((0,S.fetchEducationYear)(null==X?void 0:X.find(n=>(null==n?void 0:n.name)===e.target.value)))},children:X.map((e,n)=>(0,i.jsx)(s.default,{value:null==e?void 0:e.name,children:null==e?void 0:e.name},null==e?void 0:e.id))})]})}),(0,i.jsx)(a.default,{sx:{width:200},children:(0,i.jsxs)(r.default,{fullWidth:!0,children:[(0,i.jsx)(o.default,{id:"eduType",children:"Ta'lim shakli"}),(0,i.jsxs)(u.default,{labelId:"eduType",id:"demo-simple-select-edu-type",value:ea,label:"Ta'lim shakli",onChange:e=>er(e.target.value),children:[(0,i.jsx)(s.default,{value:"KUNDUZGI",children:"KUNDUZGI"}),(0,i.jsx)(s.default,{value:"KECHKI",children:"KECHKI"}),(0,i.jsx)(s.default,{value:"SIRTQI",children:"SIRTQI"})]})]})}),(0,i.jsx)(a.default,{sx:{width:200},children:(0,i.jsxs)(r.default,{fullWidth:!0,children:[(0,i.jsx)(o.default,{id:"kurs",children:"Kurs"}),(0,i.jsxs)(u.default,{labelId:"kurs",id:"demo-simple-selected-kurs",value:eo,label:"Kurs",onChange:e=>eu(e.target.value),children:[(0,i.jsx)(s.default,{value:1,children:"1"}),(0,i.jsx)(s.default,{value:2,children:"2"}),(0,i.jsx)(s.default,{value:3,children:"3"}),(0,i.jsx)(s.default,{value:4,children:"4"})]})]})}),(0,i.jsx)(a.default,{sx:{width:200},children:(0,i.jsxs)(r.default,{fullWidth:!0,children:[(0,i.jsx)(o.default,{id:"demo-simple-select-label",children:"Yo'nalish"}),(0,i.jsx)(u.default,{labelId:"demo-simple-select-label",id:"demo-simple-select",value:l||"",label:"Yo'nalish",onChange:e=>{t(e.target.value)},children:el.map((e,n)=>(0,i.jsx)(s.default,{value:null==e?void 0:e.name,children:null==e?void 0:e.name},null==e?void 0:e.id))})]})}),(0,i.jsx)(a.default,{sx:{width:200},children:(0,i.jsxs)(r.default,{fullWidth:!0,children:[(0,i.jsx)(o.default,{id:"demo-simple-select-label1",children:"Group"}),(0,i.jsx)(u.default,{labelId:"demo-simple-select-label1",id:"demo-simple-select1",value:v||"",label:"Group",onChange:e=>{p(e.target.value),ev(!0)},children:null==ee?void 0:ee.map((e,n)=>(0,i.jsx)(s.default,{value:null==e?void 0:e.name,children:null==e?void 0:e.name},null==e?void 0:e.id))})]})}),(0,i.jsx)(a.default,{sx:{width:200},children:(0,i.jsx)(r.default,{fullWidth:!0,children:(0,i.jsx)(c.default,{label:"Date",InputLabelProps:{shrink:!0},type:"date",InputProps:{inputProps:{max:y()(m.serverTimeStorage).format("YYYY-MM-DD")}},defaultValue:y()(m.serverTimeStorage).format("YYYY-MM-DD"),onChange:e=>q(e.target.value)})})})]}),(0,i.jsx)(Y.Card,{children:(0,i.jsxs)(Y.CardContent,{children:[(0,i.jsx)(L,{children:(0,i.jsxs)("table",{children:[(0,i.jsx)("thead",{children:(0,i.jsxs)("tr",{children:[(0,i.jsx)("th",{children:"\u2116"}),(0,i.jsx)("th",{children:(0,i.jsx)(a.default,{display:"flex",gap:"8px",alignItems:"center",justifyContent:"center",children:(0,i.jsx)("p",{children:"Full name"})})}),ex&&(null===(n=ed[0])||void 0===n?void 0:null===(e=n.subjects)||void 0===e?void 0:e.slice().sort((e,n)=>(null==e?void 0:e.section)<(null==n?void 0:n.section)?-1:(null==e?void 0:e.section)>(null==n?void 0:n.section)?1:0).map((e,n)=>(0,i.jsx)(I.default,{onClick:()=>{ef(e),U(!0)},title:(0,i.jsxs)("div",{style:{fontSize:12},children:[(0,i.jsx)("p",{children:null==e?void 0:e.lesson}),(0,i.jsxs)("p",{children:["room: ",null==e?void 0:e.room]}),(0,i.jsxs)("p",{children:["time: ",null==e?void 0:e.betweenDuringDate]})]}),arrow:!0,placement:"top",children:(0,i.jsx)("th",{style:{cursor:"pointer",backgroundColor:ew(e)&&"darkblue"},children:n+1})},n))),(0,i.jsx)("th",{children:"Enter"}),(0,i.jsx)("th",{children:"Attendance"})]})}),(0,i.jsx)("tbody",{children:"loading"!==ei&&(null==ed?void 0:ed.map((e,n)=>{var l;return(0,i.jsxs)("tr",{children:[(0,i.jsx)("td",{style:{textAlign:"center"},children:n+1}),(0,i.jsx)("td",{children:null==e?void 0:e.fullName}),null==e?void 0:null===(l=e.subjects)||void 0===l?void 0:l.slice().sort((e,n)=>(null==e?void 0:e.section)<(null==n?void 0:n.section)?-1:(null==e?void 0:e.section)>(null==n?void 0:n.section)?1:0).map((e,n)=>(0,i.jsx)("td",{style:{backgroundColor:ew(e)&&"rgba(0,0,0,0.07)"},children:(0,i.jsx)(_.default,{statistics:e.statistics,st:e})},n)),(0,i.jsx)("td",{children:(0,i.jsx)(I.default,{title:(null==e?void 0:e.entering)&&y()(null==e?void 0:e.entering).format("HH:mm"),arrow:!0,placement:"right",children:(0,i.jsx)(P,{color:(null==e?void 0:e.entering)?"green":"red",children:e.entering?(0,i.jsx)(M.AiFillCheckCircle,{size:20}):(0,i.jsx)(M.AiFillCloseCircle,{size:20})})})}),(0,i.jsx)("td",{children:(0,i.jsx)(H.default,{studentData:e,handleOpen:em})})]},n)}))})]})}),"loading"===ei&&(0,i.jsx)(a.default,{sx:{width:"100%"},children:(0,i.jsx)(C.default,{})}),"loading"!==ei&&0===ed.length&&(0,i.jsx)(a.default,{children:(0,i.jsx)(F.default,{w:200,h:180})})]})}),(0,i.jsx)(h.default,{open:k,onClose:ej,"aria-labelledby":"modal-modal-title","aria-describedby":"modal-modal-description",children:(0,i.jsxs)(a.default,{sx:K,component:N,children:[(0,i.jsx)(G,{onClick:ej,children:(0,i.jsx)(f.RiCloseLine,{})}),(0,i.jsx)(x.default,{data:W})]})}),(0,i.jsx)(h.default,{open:T,onClose:ej,"aria-labelledby":"modal-modal-title","aria-describedby":"modal-modal-description",children:(0,i.jsxs)(a.default,{sx:z,component:N,children:[(0,i.jsx)(G,{onClick:()=>E(!1),children:(0,i.jsx)(f.RiCloseLine,{})}),(0,i.jsx)(j.default,{data:W})]})}),(0,i.jsx)(h.default,{open:A,onClose:()=>U(!1),"aria-labelledby":"modal-modal-title","aria-describedby":"modal-modal-description",children:(0,i.jsxs)(a.default,{sx:B,component:N,children:[(0,i.jsx)(G,{onClick:()=>U(!1),children:(0,i.jsx)(f.RiCloseLine,{})}),(0,i.jsx)(D.default,{subjectInfo:eh})]})})]})}},38855:function(e,n,l){"use strict";l.r(n);var t=l("58865");l("31672"),l("59461"),l("2490"),l("57640"),l("9924");var i=l("85893");l("67294");var d=l("71893"),a=l("16393");function r(){let e=(0,t._)(["\n  width: 100%;\n  padding: 5px;\n  border: 1px solid #cebfbf;\n  text-align: center;\n"]);return r=function(){return e},e}function o(){let e=(0,t._)(["\n  background: ",";\n  color: ",";\n  display: grid;\n  font-weight:  ",";\n  grid-template-columns: 0.5fr 0.3fr 0.9fr 0.3fr 0.3fr 0.3fr;\n"]);return o=function(){return e},e}function u(){let e=(0,t._)(["\n\n"]);return u=function(){return e},e}function s(){let e=(0,t._)(["\n  margin-top: 50px;\n  padding: 10px;\n  min-height: 300px;\n  max-height: 350px;\n  overflow-y: scroll;\n"]);return s=function(){return e},e}function c(){let e=(0,t._)(["\n  position: absolute;\n  font-size: 20px;\n  color: #ffffff;\n  background-color: ",";\n  width: 100%;\n  top: -1px;\n  border-radius: 8px 8px 0 0;\n  padding: 10px;\n  z-index: 9;\n"]);return c=function(){return e},e}function h(){let e=(0,t._)(["\n"]);return h=function(){return e},e}let f=d.default.div(r()),x=d.default.div(o(),e=>e.bg||"#FFF",e=>"red"===e.bg?"#FFF":"#000",e=>e.fw||"normal"),v=d.default.div(u()),p=d.default.div(s()),m=d.default.div(c(),a.mainColor),j=d.default.div(h());n.default=e=>{let{data:n}=e,{studentResults:l,studentName:t}=n,d=e=>{let n=[{alph:"A+",Min:95,Max:100},{alph:"A",Min:90,Max:94},{alph:"B+",Min:85,Max:89},{alph:"B",Min:80,Max:84},{alph:"C+",Min:75,Max:79},{alph:"C",Min:70,Max:74},{alph:"D+",Min:65,Max:69},{alph:"D",Min:60,Max:64},{alph:"F",Min:0,Max:59},{alph:"FA",Min:0,Max:0}];return 0!==e?n.find(n=>n.Min<=e):n.find(n=>n.Min<=e&&n.Max<=e)};return(0,i.jsxs)(j,{children:[(0,i.jsx)(m,{children:t}),(0,i.jsx)(p,{children:(0,i.jsxs)(v,{children:[(0,i.jsxs)(x,{fw:"bold",bg:"#f3efef",children:[(0,i.jsx)(f,{children:"Year"}),(0,i.jsx)(f,{children:"Semester"}),(0,i.jsx)(f,{children:"Subject"}),(0,i.jsx)(f,{children:"Credit"}),(0,i.jsx)(f,{children:"Score"}),(0,i.jsx)(f,{children:"Grade"})]}),l.map((e,n)=>(0,i.jsxs)(x,{bg:e.allScore<=59&&"red",children:[(0,i.jsx)(f,{children:e.year}),(0,i.jsx)(f,{children:e.semestr}),(0,i.jsx)(f,{children:e.subject}),(0,i.jsx)(f,{children:e.credit}),(0,i.jsxs)(f,{children:[e.allScore,"%"]}),(0,i.jsx)(f,{children:d(e.allScore).alph})]},n))]})})]})}}}]);