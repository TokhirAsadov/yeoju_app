/*! For license information please see 8469.f963eee8.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["8469"],{8924:function(e,l,t){"use strict";t.r(l);var n=t("58865");t("33948"),t("31672"),t("59461"),t("2490"),t("57658"),t("88449"),t("19894"),t("57640"),t("9924");var i=t("85893"),r=t("67294"),o=t("71893"),a=t("57024"),d=t("52861"),s=t("44118"),u=t("27233"),c=t("16393"),h=t("30381"),m=t.n(h),x=t("32392"),f=t("44025"),v=t("61261"),b=t("42154"),p=t("96413"),g=t("73871"),j=t("14628"),T=t("78951"),y=t("53432"),C=t("16242");function w(){let e=(0,n._)(["\n    width: 300px;\n    ","\n"]);return w=function(){return e},e}function D(){let e=(0,n._)(["\n    overflow: hidden;\n    margin: 15px 0 0;\n    padding: 10px 0;\n    display: flex;\n    flex-wrap: wrap;\n    align-items: center;\n    gap: 25px;\n    ","\n"]);return D=function(){return e},e}function S(){let e=(0,n._)(["\n    width: 100%;\n    display: flex;\n    flex-direction: column;\n    gap: 20px;\n    padding: 1rem;\n\n"]);return S=function(){return e},e}let k=o.default.div(w(),(0,a.extrasmall)({width:"80%"})),Y=o.default.div(D(),(0,a.extrasmall)({justifyContent:"center"})),M=o.default.div(S());l.default=()=>{var e,l,t,n,o;let[a,h]=(0,r.useState)(!1),[w,D]=(0,r.useState)([]),[S,W]=(0,r.useState)([]),[N,R]=(0,r.useState)([]),[A,F]=(0,r.useState)(null),[O,z]=(0,r.useState)((null==N?void 0:null===(e=N.find(e=>(null==e?void 0:e.label)===A))||void 0===e?void 0:e.minFloor)||1),{headers:_}=(0,c.getHeaders)(),[B,E]=(0,r.useState)(m()(c.serverTimeStorage)),[H,P]=(0,r.useState)(!1);(0,r.useEffect)(()=>{u.default.get(c.BASE_URL+"/building/getBuildingsForSelect",{headers:_}).then(e=>{var l,t,n;R(null==e?void 0:null===(l=e.data)||void 0===l?void 0:l.obj),F(null==e?void 0:null===(n=e.data)||void 0===n?void 0:null===(t=n.obj[0])||void 0===t?void 0:t.label)}).catch(e=>{})},[]);let[I,L]=(0,r.useState)({date:new Date(c.serverTimeStorage),dateFrom:m()(c.serverTimeStorage).startOf("isoWeek").toDate(),dateTo:m()(c.serverTimeStorage).endOf("isoWeek").toDate(),weekNumber:m()(c.serverTimeStorage).isoWeek(),year:m()(c.serverTimeStorage).year(),weekday:m()(c.serverTimeStorage).day()}),U=e=>{let l=m()(e.format("YYYY-MM-DD")).isoWeek(),t=m()(e.format("YYYY-MM-DD")).day(),n=m()(e.format("YYYY-MM-DD")).year(),i=m()(e.format("YYYY-MM-DD")).startOf("isoWeek").toDate(),r=m()(e.format("YYYY-MM-DD")).endOf("isoWeek").toDate();L({date:e,dateFrom:i,dateTo:r,weekNumber:l,year:n,weekday:t})};(0,r.useEffect)(()=>{h(!1),P(!1),A&&V()},[A,O,I]),(0,r.useEffect)(()=>{U(B)},[B]);let V=()=>{u.default.get(c.BASE_URL+"/timeTableByWeekOfYear/getTimeTableByAllRoomAndWeek?b=".concat(null==A?void 0:A.substring(0,1),"-").concat(O,"&week=").concat(null==I?void 0:I.weekNumber,"&weekday=").concat(I.weekday,"&year=").concat(I.year,"&t=true"),{headers:_}).then(e=>{var l;D(e.data.obj),W(null==e?void 0:null===(l=e.data)||void 0===l?void 0:l.secondObj),h(!0),P(!1)}).catch(e=>{P(!0)})};return(0,i.jsxs)(M,{children:[N&&(null==N?void 0:N.length)>0&&(0,i.jsxs)(Y,{children:[(0,i.jsx)(k,{children:(0,i.jsx)(s.default,{disablePortal:!0,id:"combo-box-demo",options:N,defaultValue:N[0],onChange:(e,l)=>{F(null==l?void 0:l.label)},renderInput:e=>(0,i.jsx)(d.default,{...e,label:"Buildings"})})}),(0,i.jsx)(k,{children:(0,i.jsxs)(b.default,{fullWidth:!0,children:[(0,i.jsx)(x.default,{id:"demo-simple-select-label",children:"Floor"}),(0,i.jsx)(f.default,{labelId:"demo-simple-select-label",id:"demo-simple-select",value:O,label:"Floor",onChange:e=>{z(e.target.value)},children:null===(t=function(e,l){let t=[];for(;e<=l;e++)t.push(e);return t}(null==N?void 0:null===(n=N.find(e=>(null==e?void 0:e.label)===A))||void 0===n?void 0:n.minFloor,null==N?void 0:null===(o=N.find(e=>(null==e?void 0:e.label)===A))||void 0===o?void 0:o.maxFloor))||void 0===t?void 0:null===(l=t.filter(e=>0!==e))||void 0===l?void 0:l.map(e=>(0,i.jsx)(v.default,{value:e,children:e}))})]})}),(0,i.jsx)(g.LocalizationProvider,{dateAdapter:p.AdapterMoment,children:(0,i.jsx)(j.DesktopDatePicker,{label:"Day",inputFormat:"DD/MM/YYYY",value:B,onChange:e=>{E(e)},renderInput:e=>(0,i.jsx)(d.default,{...e})})})]}),(0,i.jsx)("div",{style:{width:"100%",overflowX:"scroll"},children:a?(0,i.jsx)(C.default,{obj:w,secondObj:S,date:I}):H?(0,i.jsx)(T.default,{w:"200",h:"180"}):(0,i.jsx)(y.default,{})})]})}},26457:function(e,l,t){"use strict";t.r(l);var n=t("58865");t("31672"),t("59461"),t("2490"),t("88449"),t("19894"),t("57640"),t("9924");var i=t("85893"),r=t("67294"),o=t("7104"),a=t("71893"),d=t("26178"),s=t("78951"),u=t("30381"),c=t.n(u),h=t("50533"),m=t("5434"),x=t("51649");function f(){let e=(0,n._)(["\n    color: ",";\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    gap: 5px;\n"]);return f=function(){return e},e}let v=(0,r.forwardRef)(function(e,l){return(0,i.jsx)(o.Slide,{direction:"up",ref:l,...e})}),b=(0,d.createTheme)({components:{MuiTableCell:{styleOverrides:{root:{justifyContent:"center",textAlign:"center",border:"1px solid #ccc",padding:"10px"}}}}}),p=a.default.span(f(),e=>e.color?"green":"red");l.default=(0,r.memo)(e=>{var l,t,n;let{open:r,handleClose:a,data:u}=e,f=(0,h.useSelector)(e=>e.hourSection),g=e=>{var l,t,n;let r=null==e?void 0:e.login,o=null==e?void 0:e.hourNumber,a=null===(l=f.find(e=>e.number===o))||void 0===l?void 0:l.start,d=null==u?void 0:null===(t=u.enterRoomTimeFilterWithoutLogin)||void 0===t?void 0:t.filter(e=>(null==e?void 0:e.section)===o&&(null==e?void 0:e.login)===r).sort((e,l)=>new Date(e.time)-new Date(l.time));if(!d||0===d.length)return(0,i.jsx)(x.IoMdCloseCircle,{size:20,color:"red"});let s=null===(n=d[0])||void 0===n?void 0:n.time,h=new Date(a).setDate(new Date(s).getDate())>=s;return(0,i.jsxs)(p,{color:h,children:[h?(0,i.jsx)(m.MdOutlineAddCircleOutline,{size:16}):(0,i.jsx)(m.MdRemoveCircleOutline,{size:16}),(0,i.jsx)("p",{children:c()(Math.abs(new Date(a).setDate(new Date(s).getDate())-s)).format("mm:ss")})]})};return r&&(0,i.jsxs)(o.Dialog,{open:r,maxWidth:"md",TransitionComponent:v,keepMounted:!0,onClose:a,"aria-labelledby":"selected-lesson-dialog-title",fullWidth:!0,children:[(0,i.jsxs)(o.DialogTitle,{id:"selected-lesson-dialog-title",children:[null==u?void 0:null===(l=u.lessonFilter[0])||void 0===l?void 0:l.room," ",null==u?void 0:null===(t=u.lessonFilter[0])||void 0===t?void 0:t.periodStartAndEndTime]}),(0,i.jsx)(o.DialogContent,{sx:{padding:"0 5px"},children:(0,i.jsx)(d.ThemeProvider,{theme:b,children:(0,i.jsx)(o.TableContainer,{component:o.Paper,children:(0,i.jsxs)(o.Table,{sx:{minWidth:450},"aria-label":"selected-hour-lessons",children:[(0,i.jsx)(o.TableHead,{children:(0,i.jsxs)(o.TableRow,{children:[(0,i.jsx)(o.TableCell,{children:(0,i.jsx)("b",{children:"Teacher"})}),(0,i.jsx)(o.TableCell,{children:(0,i.jsx)("b",{children:"Subjects"})}),(0,i.jsx)(o.TableCell,{children:(0,i.jsx)("b",{children:"Attendance"})})]})}),(0,i.jsx)(o.TableBody,{children:(null==u?void 0:null===(n=u.lessonFilter)||void 0===n?void 0:n.length)?u.lessonFilter.map((e,l)=>(0,i.jsxs)(o.TableRow,{children:[(0,i.jsx)(o.TableCell,{children:null==e?void 0:e.teacherName}),(0,i.jsx)(o.TableCell,{children:null==e?void 0:e.lessonName}),(0,i.jsx)(o.TableCell,{children:g(e)})]},l)):(0,i.jsx)(o.TableRow,{children:(0,i.jsx)(o.TableCell,{colSpan:3,children:(0,i.jsx)(s.default,{w:200,h:180})})})})]})})})}),(0,i.jsx)(o.DialogActions,{children:(0,i.jsx)(o.Button,{size:"small",variant:"outlined",onClick:a,children:"Close"})})]})})},16242:function(e,l,t){"use strict";t.r(l),t.d(l,{default:function(){return v}});var n=t("58865");t("33792"),t("57640"),t("9924"),t("33948"),t("25387"),t("72608"),t("2490"),t("31672"),t("59461"),t("57658"),t("88449"),t("19894");var i=t("85893"),r=t("67294"),o=t("50533"),a=t("7104"),d=t("71893"),s=t("30381"),u=t.n(s),c=t("3506"),h=t("26457"),m=t("16393");function x(){let e=(0,n._)(["\n    cursor: pointer;\n    color: #fff;\n    width: 23px;\n    height: 23px;\n    position: absolute;\n    left: 10px;\n    bottom: 10px;\n    font-size: 12px;\n    border-radius: 50%;\n    background-color: rgba(0, 0, 255, 0.75);\n    display: flex;\n    justify-content: center;\n    align-items: center;\n    text-align: center;\n    line-height: 1;\n"]);return x=function(){return e},e}function f(){let e=(0,n._)(["\nposition: absolute;\n    bottom: 10px;\n    right: 10px;\n"]);return f=function(){return e},e}function v(e){let{obj:l,secondObj:t,date:n}=e,d=null==l?void 0:l.flat().map(e=>{var l,t,n;return{teacherId:null===(l=e.teachers[0])||void 0===l?void 0:l.id,teacherName:null===(t=e.teachers[0])||void 0===t?void 0:t.name,login:null===(n=e.teachers[0])||void 0===n?void 0:n.shortName,room:e.room.substring(0,5),lessonName:e.lessonName,hourNumber:e.hourNumber,periodStartAndEndTime:e.periodStartAndEndTime}}).sort((e,l)=>e.room.localeCompare(l.room)),s=t.flat(),x=(0,o.useSelector)(e=>e.hourSection),[f,v]=(0,r.useState)(!1),[g,j]=(0,r.useState)(null),T=e=>{j(e),v(!0)},y=d.reduce((e,l)=>{let t=e.find(e=>e[0].room===l.room);return t?t.push(l):e.push([l]),e},[]),C=(e,l)=>{var t;let n=u()(e),r=u()(null===(t=x.find(e=>e.number===l))||void 0===t?void 0:t.start).date(n.date()).diff(n,"milliseconds"),o=u().utc(Math.abs(r)).format("mm:ss");return r<0?(0,i.jsx)(c.default,{title:n.format("HH:mm"),arrow:!0,children:(0,i.jsx)(a.Chip,{label:o,color:"warning",size:"small"})}):r>0?(0,i.jsx)(c.default,{title:n.format("HH:mm"),arrow:!0,children:(0,i.jsx)(a.Chip,{label:o,color:"success",size:"small"})}):(0,i.jsx)(a.Chip,{label:"On time",color:"default",size:"small"})};return(0,i.jsxs)(a.TableContainer,{component:a.Paper,style:{marginTop:20},children:[(0,i.jsxs)(a.Table,{children:[(0,i.jsx)(a.TableHead,{children:(0,i.jsxs)(a.TableRow,{children:[(0,i.jsx)(a.TableCell,{align:"center",sx:{borderRight:"1px solid rgba(224, 224, 224, 1)",minWidth:"85px"},children:(0,i.jsx)(a.Typography,{variant:"subtitle1",fontWeight:"bold",children:"Time"})}),x.map((e,l)=>(0,i.jsx)(a.TableCell,{align:"center",sx:{borderRight:"1px solid rgba(224, 224, 224, 1)",minWidth:"200px"},children:(0,i.jsx)(a.Typography,{variant:"subtitle2",children:e.hour})},l))]})}),(0,i.jsx)(a.TableBody,{children:y.map((e,l)=>(0,i.jsxs)(a.TableRow,{children:[(0,i.jsx)(a.TableCell,{sx:{borderRight:"1px solid rgba(224, 224, 224, 1)"},children:(0,i.jsx)(a.Typography,{variant:"body1",fontWeight:"medium",children:e[0].room})}),x.map((l,t)=>{let r=e.find(e=>e.hourNumber===l.number),o=e.filter(e=>e.hourNumber===l.number),d=s.filter(l=>l.room===e[0].room&&l.section===(null==r?void 0:r.hourNumber)&&l.login===(null==r?void 0:r.login)).sort((e,l)=>e.time-l.time),c=s.filter(l=>l.room===e[0].room&&l.section===(null==r?void 0:r.hourNumber)),h=u()(n.date).set({hour:u()(l.start).hours(),minute:u()(l.start).minutes()}),x=u()(m.serverTimeStorage).isAfter(h)&&(d.length>0?C(d[0].time,d[0].section):(0,i.jsx)(a.Chip,{label:"Missed lesson",color:"error",size:"small"}));return(0,i.jsx)(a.TableCell,{align:"center",sx:{borderRight:"1px solid rgba(224, 224, 224, 1)",position:"relative",height:195},children:r?(0,i.jsxs)(i.Fragment,{children:[o.length>1&&(0,i.jsx)(b,{onClick:()=>T({enterRoomTimeFilterWithoutLogin:c,lessonFilter:o}),children:o.length}),(0,i.jsx)(a.Typography,{variant:"body2",fontWeight:"medium",children:r.lessonName}),(0,i.jsx)(a.Typography,{variant:"body2",color:"textSecondary",children:r.teacherName}),(0,i.jsx)(p,{children:x})]}):"-"},t)})]},l))})]}),(0,i.jsx)(h.default,{open:f,handleClose:()=>{v(!1),j(null)},data:g})]})}let b=d.default.span(x()),p=d.default.div(f())}}]);