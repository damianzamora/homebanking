const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            accounts:[],
            tipoCuenta:"",
            
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data
             this.accounts=res.data.accounts.filter(e=> e.active==true)
            })
    },
    methods:{
        CrearAccount(){
            Swal.fire({
                title: 'Desea crear la Cuenta?',
                text: "",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/accounts',
                    "tipoCuenta="+this.tipoCuenta,
                    {headers:{'accept':'application/xml'}})
                  .then(res => Swal.fire(
                   'Cuenta creada con éxito!',
                    'Serás redireccionado a tus cuentas',
                    'success',))
			      .then(response => window.location.href = "accounts.html")
                  .catch(response=> Swal.fire("Alcanzó el limite de 3 cuentas","No puede crear mas cuentas","error"))
                  
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