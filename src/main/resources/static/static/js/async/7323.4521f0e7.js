/*! For license information please see 7323.4521f0e7.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["7323"],{24195:function(e,t,i){"use strict";i.r(t);var a=i("58865");i("33948"),i("57640"),i("9924");var n=i("85893"),d=i("67294"),l=i("71893"),r=i("16356"),o=i("53432"),s=i("16393"),c=i("27233"),u=i("57024");function m(){let e=(0,a._)(["\n  width: 100%;\n  background-color: #fff;\n  border-radius: 10px;\n  display: flex;\n  align-items: center;\n  justify-content: space-between;\n"]);return m=function(){return e},e}function f(){let e=(0,a._)(["\n  color: ",";\n  margin: 10px 0 20px 20px;\n  ","\n"]);return f=function(){return e},e}function h(){let e=(0,a._)(["\n  padding: 5px 10px!important; \n"]);return h=function(){return e},e}let p=l.default.div(m()),b=l.default.h3(f(),s.mainColor,(0,u.extrasmall)({textAlign:"center",marginLeft:0})),g=l.default.div(h());t.default=()=>{let[e,t]=(0,d.useState)(!0),[i,a]=(0,d.useState)([{id:1,fullName:"Tohir Asadov",cardNo:"123456",dateAsc:new Date,dateDesc:new Date,phoneNumber:"993361603"},{id:2,fullName:"Tohir Asadov",cardNo:"123456",dateAsc:new Date,dateDesc:new Date,Print:"OK See",phoneNumber:"993361603"}]);return(0,d.useEffect)(()=>{let{headers:e}=(0,s.getHeaders)();c.default.get(s.BASE_URL+s.ADMIN.MENU,{headers:e}).then(e=>{var i;null===(i=e.data)||void 0===i||i.map(async e=>(null!==e.time&&(e.time=new Date(e.time)),e)),a(e.data),t(!1)}).catch(e=>void 0)},[]),(0,n.jsxs)(g,{children:[(0,n.jsx)(b,{children:"Statistics Table"}),(0,n.jsx)(p,{children:e?(0,n.jsx)(o.default,{}):(0,n.jsx)(r.DataGrid,{checkboxSelection:!0,style:{width:"900px",minHeight:"300px!important"},columns:[{field:"id",headerName:"ID",width:40,editable:!1},{field:"fullName",headerName:"Full Name",type:"dateTime",width:300,editable:!1},{field:"device",headerName:"Device",type:"string",width:180,editable:!1},{field:"room",headerName:"Room",type:"string",width:140,editable:!1},{field:"time",headerName:"Time",type:"dateTime",width:300,editable:!1},{field:"passport",headerName:"Passport",type:"string",width:150,editable:!1},{field:"card",headerName:"Card",type:"string",width:150,editable:!1},{field:"login",headerName:"Login",type:"string",width:150,editable:!1}],rows:i,components:{Toolbar:r.GridToolbar},autoHeight:!0,pageSize:50,initialState:{...i.initialState,columns:{columnVisibilityModel:{id:!1,login:!1,card:!1,passport:!1}}}})})]})}}}]);