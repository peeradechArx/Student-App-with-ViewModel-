@file:OptIn(ExperimentalMaterial3Api::class)

package peeradech.p.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import peeradech.p.lab04.ui.theme.LAB04Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB04Theme {
                // A surface container using the 'background' color from the theme
                StudentApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentApp() {
    var viewModel = StudentViewModel()
    var isDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student App")},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Magenta,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isDialogVisible = true
                },
                containerColor = Color.Magenta,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "Add new student")
            }
        }

    ){
        LazyColumn(
            modifier = Modifier.padding(it)){
            items(viewModel.data){
                    student -> Text("Name: ${student.name} | ID: ${student.studentId}")
            }
        }

        if (isDialogVisible) {
            showAddStudentDialog(
                onDismiss = { isDialogVisible = false },
                onAddStudent = { addedName, addedStudentId ->
                    viewModel.addStudent(addedName, addedStudentId)
                    isDialogVisible = false
                }
            )
        }
    }
}

@Composable
fun showAddStudentDialog(
    onDismiss: () -> Unit,
    onAddStudent: (name: String, studentId: String) -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var studentId by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text("Add New Student")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { newName -> name = newName },
                    label = { Text("Name") },
                    leadingIcon = { Icon(Icons.Outlined.Person, "Person") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = studentId,
                    onValueChange = { newStudentId -> studentId = newStudentId },
                    label = { Text("Student ID") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddStudent(name.text, studentId.text)
                },colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Magenta)
            ) {
                Icon(Icons.Filled.Done, contentDescription = "Done")
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Magenta)
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LAB04Theme {
        StudentApp()
    }
}
