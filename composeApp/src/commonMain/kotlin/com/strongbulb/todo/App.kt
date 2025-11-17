package com.strongbulb.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete

// TODO 데이터 모델
data class TodoItem(
    val id: Int,
    val text: String,
    val isDone: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        // 상태 관리
        var text by remember { mutableStateOf("") }
        var todoItems by remember { mutableStateOf(listOf<TodoItem>()) }

        // 완료된 항목 개수 계산
        val completedCount = todoItems.count { it.isDone }
        val totalCount = todoItems.size

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Column {
                            Text("Todo App")
                            if (totalCount > 0) {
                                Text(
                                    text = "완료: $completedCount / 전체: $totalCount",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        addTodoItem(text, todoItems) { newItem ->
                            todoItems = todoItems + newItem
                            text = ""
                        }
                    }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "할 일 추가")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(19.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 할 일 입력 필드
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("할 일을 입력하세요") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            addTodoItem(text, todoItems) { newItem ->
                                todoItems = todoItems + newItem
                                text = ""
                            }
                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 할 일 목록 표시
                if (todoItems.isEmpty()) {
                    // 빈 목록일 때 메시지 표시
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "할 일을 추가해보세요!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(todoItems, key = { it.id }) { item ->
                            TodoListItem(
                                item = item,
                                onToggleDone = {
                                    todoItems = todoItems.map {
                                        if (it.id == item.id) {
                                            it.copy(isDone = !it.isDone)
                                        } else {
                                            it
                                        }
                                    }
                                },
                                onDelete = {
                                    todoItems = todoItems.filter { it.id != item.id }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// 할 일 추가 헬퍼 함수
fun addTodoItem(
    text: String,
    currentItems: List<TodoItem>,
    onAdd: (TodoItem) -> Unit
) {
    if (text.isNotBlank()) {
        val newItem = TodoItem(
            id = (currentItems.maxOfOrNull { it.id } ?: 0) + 1,
            text = text.trim()
        )
        onAdd(newItem)
    }
}

// TODO 항목 컴포저블
@Composable
fun TodoListItem(
    item: TodoItem,
    onToggleDone: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isDone) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isDone,
                onCheckedChange = { onToggleDone() }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = item.text,
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onToggleDone),
                textDecoration = if (item.isDone) TextDecoration.LineThrough else TextDecoration.None,
                color = if (item.isDone) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "삭제",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
