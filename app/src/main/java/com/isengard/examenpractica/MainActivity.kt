package com.isengard.examenpractica

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Log.d("FraguasIsengard", "onCreate: Las fraguas de Isengard se han forjado en la memoria")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //vinculamos la parte gráfica
        val nombre = findViewById<EditText>(R.id.nombreUh)
        val rol = findViewById<Spinner>(R.id.TipUd)
        val equipo = findViewById<RadioGroup>(R.id.cajaOpc)
        val armadura = findViewById<RadioButton>(R.id.opc1)
        val escudo = findViewById<RadioButton>(R.id.opc2)
        val antorcha = findViewById<CheckBox>(R.id.cajaCheck)
        val envio = findViewById<ImageButton>(R.id.envioTp)

        //pasa los datos del spinner desde strings.xml
        val adaptador = ArrayAdapter.createFromResource(
            this,
            R.array.tiposUnidad,
            android.R.layout.simple_spinner_item
        )
        //como se ve el menú desplegable
        adaptador.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        rol.adapter = adaptador

        //foco inicial al nombre/EditText
        nombre.requestFocus()

        //comprobar si el usuario sale del foco del campo nombre
        nombre.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && nombre.text.toString().trim().isEmpty()) {
                nombre.error = "El ejército no acepta soldados anónimos"
            }
        }

        //acción del botón de envío, lo que esté dentro de las llaves, solo se ejecutará cuando pulsen el botón
        envio.setOnClickListener {

            //leemos el texto que escribió el usuario
            val textoEnvio = nombre.text.toString().trim()

            //valida si está vacío. Si está vacío, detenemos el botón
            if (textoEnvio.isEmpty()) {
                nombre.error = "No se aceptan soldados anónimos"
                nombre.requestFocus()
                return@setOnClickListener
            }

            //si el nombre es válido, lee los demás componentes gráficos
            val tipoUnidad = rol.selectedItem.toString()

            //lee si seleccionó armadura, escudo o antorcha:
            val llevarAntorcha = antorcha.isChecked
            val opcEquipoSelec = equipo.checkedRadioButtonId

            //muestra el toast de larga duración con un mensaje
            Toast.makeText(
                this,
                "¡Unidad " + textoEnvio + " enviada al Abismo de Helm!",
                Toast.LENGTH_LONG
            ).show();
        }
    }

    //Logs
    override fun onStart() {
        super.onStart()
        Log.d("FraguasIsengard", "onStart: Las fraguas se encienden y el ejército se prepara")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FraguasIsengard", "onResume: Saruman continúa la producción")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FraguasIsengard", "onPause: Saruman detiene la producción temporalmente")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FraguasIsengard", "onStop: Saruman detiene la producción definitivamente")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FraguasIsengard", "onDestroy: Las fraguas son destruidas")
    }

    //para guardar los datos al girar la pantalla
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val campoNombre = findViewById<EditText>(R.id.nombreUh)
        //comprueba si hay un error en el texto
        if (campoNombre.error != null) {
            outState.putString("error", campoNombre.error.toString())
        }
    }
}