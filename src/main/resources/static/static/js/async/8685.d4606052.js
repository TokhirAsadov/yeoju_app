/*! For license information please see 8685.d4606052.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["8685"],{11476:function(e,t,n){"use strict";n.r(t);var i=n("58865");n("33948"),n("57640"),n("9924");var a=n("85893"),r=n("67294"),d=n("71893"),l=n("53432"),o=n("16356"),u=n("27233"),s=n("16393"),c=n("55693"),f=n("94718"),h=n("39711"),p=n("60155");function m(){let e=(0,i._)(["\n  width: 100%;\n  background-color: #fff;\n  border-radius: 10px;\n  display: flex;\n  align-items: center;\n  justify-content: space-between;\n"]);return m=function(){return e},e}function x(){let e=(0,i._)(["\n  width: 100%;\n  background-color: ",";\n  padding: 5px 10px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  margin: 5px!important;\n  border-radius: 10px;\n"]);return x=function(){return e},e}function S(){let e=(0,i._)(["\n  color: ",";\n  font-size: 20px;\n"]);return S=function(){return e},e}function b(){let e=(0,i._)(["\n  height: 100%;\n  min-height: 500px;\n  border-radius: 10px;\n  margin-top: 10px!important;\n  padding: 5px 10px!important; \n"]);return b=function(){return e},e}let g=d.default.div(m()),w=d.default.div(x(),e=>"SEND"===e.type?"lime":"SENDING"===e.type?"yellow":"red"),y=d.default.h3(S(),s.mainColor),N=d.default.div(b());t.default=()=>{let[e,t]=(0,r.useState)(!0),n=(0,h.useNavigate)(),i=[{field:"id",headerName:"ID",width:40,editable:!1},{field:"createdAt",headerName:"Created Time",type:"dateTime",width:200,editable:!1},{field:"smsType",headerName:"TYPE OF SMS",width:200,editable:!1},{field:"messageBody",headerName:"MESSAGE",width:400,editable:!1},{field:"status",headerName:"STATUS",width:100,editable:!1,renderCell:e=>(0,a.jsx)(w,{type:e.row.status,children:e.row.status})},{field:"course",headerName:"COURSE",width:40,editable:!1},{field:"groupName",headerName:"GROUP NAME",width:200,editable:!1},{field:"user",headerName:"USER",width:400,editable:!1}],[d,m]=(0,r.useState)([]);return(0,r.useEffect)(()=>{let e=localStorage.getItem(s.TOKEN),t={Authorization:s.TokenType+e,"Access-Control-Allow-Origin":"*"};u.default.get(s.BASE_URL+s.DEKAN.GET_MESSAGES_HISTORY,{headers:t}).then(e=>{e.data.map(async e=>(null!==e.createdAt&&(e.createdAt=new Date(e.createdAt)),e)),m(e.data)}).catch(e=>{})},[]),(0,a.jsxs)(N,{children:[(0,a.jsxs)(c.default,{sx:{mb:3,display:"flex",justifyContent:"space-between",alignItems:"center"},children:[(0,a.jsx)(y,{children:"History of SMS"}),(0,a.jsx)(f.default,{variant:"outlined",startIcon:(0,a.jsx)(p.IoArrowBackOutline,{}),onClick:()=>n(-1),children:"back"})]}),(0,a.jsx)(g,{children:e?(0,a.jsx)(o.DataGrid,{checkboxSelection:!0,style:{width:"900px",minHeight:"300px!important"},columns:i,rows:d,components:{Toolbar:o.GridToolbar},autoHeight:!0,pageSize:5,initialState:{...d.initialState,columns:{columnVisibilityModel:{id:!1,user:!1,groupName:!1,course:!1}}}}):(0,a.jsx)(l.default,{})})]})}}}]);