import globals from 'globals'
import pluginJs from '@eslint/js'
import pluginVue from 'eslint-plugin-vue'
import * as vueParser from 'vue-eslint-parser'
import pluginPrettier from 'eslint-plugin-prettier'
import prettierConfig from './.prettierrc.js'

/** @type {import('eslint').Linter.Config[]} */
export default [
	{
		ignores: ['node_modules/**', 'dist/**', '*.d.ts', 'public/**']
	},
	{
		files: ['**/*.vue'],
		languageOptions: {
			globals: {
				...globals.browser,
				process: 'readonly',
				__dirname: 'readonly',
				module: 'readonly',
				require: 'readonly'
			},
			parser: vueParser,
			parserOptions: {
				ecmaVersion: 'latest',
				sourceType: 'module',
				ecmaFeatures: {
					jsx: true
				},
				vueFeatures: {
					filter: true,
					interpolationAsNonHTML: false
				}
			}
		},
		plugins: {
			vue: pluginVue,
			prettier: pluginPrettier
		},
		rules: {
			...pluginJs.configs.recommended.rules,
			...pluginVue.configs['flat/essential'].rules,
			'vue/multi-word-component-names': 'off',
			'prettier/prettier': ['error', prettierConfig],
			'arrow-parens': ['error', 'as-needed'],
			'arrow-body-style': 'off',
			'prefer-arrow-callback': 'off'
		}
	},
	{
		files: ['**/*.{js,mjs,cjs}'],
		languageOptions: {
			globals: {
				...globals.browser,
				process: 'readonly',
				__dirname: 'readonly',
				module: 'readonly',
				require: 'readonly'
			}
		},
		plugins: {
			prettier: pluginPrettier
		},
		rules: {
			...pluginJs.configs.recommended.rules,
			'prettier/prettier': ['error', prettierConfig],
			'arrow-parens': ['error', 'as-needed'],
			'arrow-body-style': 'off',
			'prefer-arrow-callback': 'off'
		}
	},
	{
		files: ['vite.config.js'],
		languageOptions: {
			globals: {
				process: 'readonly',
				__dirname: 'readonly',
				module: 'readonly',
				require: 'readonly'
			}
		}
	}
]
