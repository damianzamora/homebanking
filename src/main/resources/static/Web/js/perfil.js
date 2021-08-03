const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            accountsActive:[],
            cards_credit:[],
            cards_debit:[],
            pagos:[],
            loans:[],
            pagos:[],
            tipoPerfil:"",
            mailActual:"",
            mailNuevo:"",
            contraseñaActual:"",
            contraseñaNueva:"",
            
            
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data       
             this.accountsActive=res.data.accounts.filter(e=> e.active==true)
             this.cards_credit=res.data.cards.filter((e=>e.cardType=='CREDIT')).filter((e=>e.active==true))
             this.cards_debit=res.data.cards.filter((e=>e.cardType=='DEBIT')).filter((e=>e.active==true))
             this.pagos=res.data.pagos       
             this.loans=res.data.loans
            })
    },
    methods:{
        cambiarEmail(){
            
            Swal.fire({
                title: '¿Desea cambiar el mail ?',
                text: "usarás este mail para iniciar sesión",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/cambioMail',
                    "mailActual="+this.mailActual
                    +"&"+
                    "mailNuevo="+this.mailNuevo,
                    
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                  .then(res => Swal.fire(
                   'Se ha modificado los datos!',
                   'Deberas iniciar sesion nuevamente',
                    "success",))
                  .then(res=>axios.post('/api/logout'))
			      .then(response=>window.location.href = "index.html")
                  .catch(res=> Swal.fire(res.response.data,"Revise la informacion","error"))
                    }
              })   
        },
        cambiarContraseña(){
            
            Swal.fire({
                title: '¿Desea cambiar la clave ?',
                text: "usarás esta clave para iniciar sesión",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/cambioContraseña',
                    "contraseñaActual="+this.contraseñaActual
                    +"&"+
                    "contraseñaNueva="+this.contraseñaNueva,
                    
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                  .then(res => Swal.fire(
                   'Se ha modificado los datos!',
                   'Deberas iniciar sesion nuevamente',
                    "success",))
                  .then(res=>axios.post('/api/logout'))
			      .then(response=>window.location.href = "index.html")
                  .catch(res=> Swal.fire(res.response.data,"Revise la informacion","error"))
                    }
              })   
        },
       
       
        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            .then(response=>window.location.href = "index.html")
        },
    },
    
});

app.mount("#app"); 

