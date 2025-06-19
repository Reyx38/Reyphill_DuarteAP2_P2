package edu.com.reyphill_duarteap2_p2.Presentation.Repository

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.com.reyphill_duarteap2_p2.Data.Remote.Dto.RepositoryDto


@Composable
fun RepositoryListScreen(
    viewModel: RepositoryViewModel = hiltViewModel(),
    createRepository: () -> Unit,
    onEditRepository: (Int?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RepositoryListBodyScreen(
        uiState,
        createRepository,
        onEditRepository,

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListBodyScreen(
    uiState: RepositoryUiState,
    createRepository: () -> Unit,
    onEditRepository: (Int?) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var repositoryDelete by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Repositorios",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = createRepository) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Nuevo Repositorio")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(12.dp)
        ) {
            // Mostrar error si existe
            if (!uiState.errorMessage.isNullOrBlank()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = uiState.errorMessage,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Mostrar LoadingBar mientras se cargan los datos
            if (uiState.isLoading) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Cargando repositorios...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                // Mostrar lista de repositorios cuando no está cargando
                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    if (uiState.repository.isEmpty()) {
                        // Mostrar mensaje cuando no hay repositorios
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No hay repositorios registrados",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Presiona el botón + para agregar un nuevo repositorio",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            items(uiState.repository) { repository ->
                                RepositoryRow(
                                    repository,
                                    onEditRepository,
                                    onDelete = {
                                        repositoryDelete = it
                                        showDialog = true
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }

        // Diálogo de confirmación para eliminar

    }
}

@Composable
private fun RepositoryRow(
    repository: RepositoryDto,
    onEdit: (Int?) -> Unit,
    onDelete: (Int) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Header con ID y acciones
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Username: ${repository.name}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón para abrir URL
                    if (!repository.htmlUrl.isNullOrBlank()) {
                        IconButton(onClick = {
                            uriHandler.openUri(repository.htmlUrl)
                        }) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "Abrir en navegador",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }



                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del repositorio
            Text(
                text = repository.name ?: "Sin nombre",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Descripción
            if (!repository.description.isNullOrBlank()) {
                Text(
                    text = repository.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            // URL clickeable
            if (!repository.htmlUrl.isNullOrBlank()) {
                val annotatedString = buildAnnotatedString {
                    pushStringAnnotation(
                        tag = "URL",
                        annotation = repository.htmlUrl
                    )
                    pushStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                    append(repository.htmlUrl)
                    pop()
                    pop()
                }

                ClickableText(
                    text = annotatedString,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "URL",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item)
                        }
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewRepositoryList() {
    val repositories = listOf(
        RepositoryDto(
            name = "Mi Proyecto Android",
            description = "Una aplicación Android para gestionar tareas diarias con funcionalidades avanzadas de sincronización",
            htmlUrl = "https://github.com/usuario/mi-proyecto-android"
        ),
        RepositoryDto(
            name = "API REST con Spring Boot",
            description = "Backend para aplicación móvil usando Spring Boot y PostgreSQL",
            htmlUrl = "https://github.com/usuario/api-spring-boot"
        ),
        RepositoryDto(
            name = "Web App React",
            description = null,
            htmlUrl = "https://github.com/usuario/react-web-app"
        )
    )

    val mockUiState = RepositoryUiState(
        repository = repositories,
        isLoading = false
    )

    RepositoryListBodyScreen(
        uiState = mockUiState,
        createRepository = { /* Mock function */ },
        onEditRepository = { /* Mock function */ },

    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewRepositoryListLoading() {
    val mockUiState = RepositoryUiState(
        repository = emptyList(),
        isLoading = true
    )

    RepositoryListBodyScreen(
        uiState = mockUiState,
        createRepository = { /* Mock function */ },
        onEditRepository = { /* Mock function */ },

    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewRepositoryListWithError() {
    val mockUiState = RepositoryUiState(
        repository = emptyList(),
        isLoading = false,
        errorMessage = "Error al cargar los repositorios. Verifica tu conexión a internet."
    )

    RepositoryListBodyScreen(
        uiState = mockUiState,
        createRepository = { /* Mock function */ },
        onEditRepository = { /* Mock function */ },
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewRepositoryListEmpty() {
    val mockUiState = RepositoryUiState(
        repository = emptyList(),
        isLoading = false
    )

    RepositoryListBodyScreen(
        uiState = mockUiState,
        createRepository = { /* Mock function */ },
        onEditRepository = { /* Mock function */ },

    )
}